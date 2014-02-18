#include <jni.h>
#include <android_native_app_glue.h>
#include <jni.h>
#include <errno.h>

#include <EGL/egl.h>
#include <GLES/gl.h>

#include <android/log.h>
#include <android_native_app_glue.h>

#define LOGI(...) ((void)__android_log_print(ANDROID_LOG_INFO, "native-activity", __VA_ARGS__))
#define LOGW(...) ((void)__android_log_print(ANDROID_LOG_WARN, "native-activity", __VA_ARGS__))

/**
 * Our saved state data.
 */
struct saved_state {
    float angle;
    int32_t x;
    int32_t y;
};

/**
 * Shared state for our app.
 */
struct activity_state {
    struct android_app* androidApp;

    EGLDisplay egl_display;
    EGLSurface egl_surface;
    EGLContext egl_context;
    int32_t window_width;
    int32_t window_height;
    struct saved_state savedState;
};

static int init_display(struct activity_state* activity) {
    // initialize OpenGL ES and EGL

    const EGLint attribs[] = {
            EGL_SURFACE_TYPE, EGL_WINDOW_BIT,
            EGL_BLUE_SIZE, 8,
            EGL_GREEN_SIZE, 8,
            EGL_RED_SIZE, 8,
            EGL_NONE
    };
    EGLint w, h, dummy, format;
    EGLint numConfigs;
    EGLConfig config;
    EGLSurface surface;
    EGLContext context;

    EGLDisplay display = eglGetDisplay(EGL_DEFAULT_DISPLAY);

    eglInitialize(display, 0, 0);

    //pick the first config that matches
    eglChooseConfig(display, attribs, &config, 1, &numConfigs);

    eglGetConfigAttrib(display, config, EGL_NATIVE_VISUAL_ID, &format);

    ANativeWindow_setBuffersGeometry(activity->androidApp->window, 0, 0, format);

    surface = eglCreateWindowSurface(display, config, activity->androidApp->window, NULL);
    context = eglCreateContext(display, config, NULL, NULL);

    if (eglMakeCurrent(display, surface, surface, context) == EGL_FALSE) {
        LOGW("Unable to eglMakeCurrent");
        return -1;
    }

    eglQuerySurface(display, surface, EGL_WIDTH, &w);
    eglQuerySurface(display, surface, EGL_HEIGHT, &h);

    activity->egl_display = display;
    activity->egl_context = context;
    activity->egl_surface = surface;
    activity->window_width = w;
    activity->window_height = h;
    activity->savedState.angle = 0;

    // Initialize GL state.
    glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_FASTEST);
    glEnable(GL_CULL_FACE);
    glShadeModel(GL_SMOOTH);
    glDisable(GL_DEPTH_TEST);

    return 0;
}

/**
 * Just the current frame in the display.
 */
static void draw_frame(struct activity_state* activity) {
    if (activity->egl_display == NULL) {
        // No display.
        return;
    }

    // Just fill the screen with a color.
    glClearColor(((float)activity->savedState.x)/activity->window_width, activity->savedState.angle,
            ((float)activity->savedState.y)/activity->window_height, 1);
    glClear(GL_COLOR_BUFFER_BIT);

    eglSwapBuffers(activity->egl_display, activity->egl_surface);
}

/**
 * Tear down the EGL context currently associated with the display.
 */
static void close_display(struct activity_state* activity) {
    if (activity->egl_display != EGL_NO_DISPLAY) {
        eglMakeCurrent(activity->egl_display, EGL_NO_SURFACE, EGL_NO_SURFACE, EGL_NO_CONTEXT);
        if (activity->egl_context != EGL_NO_CONTEXT) {
            eglDestroyContext(activity->egl_display, activity->egl_context);
        }
        if (activity->egl_surface != EGL_NO_SURFACE) {
            eglDestroySurface(activity->egl_display, activity->egl_surface);
        }
        eglTerminate(activity->egl_display);
    }
    activity->egl_display = EGL_NO_DISPLAY;
    activity->egl_context = EGL_NO_CONTEXT;
    activity->egl_surface = EGL_NO_SURFACE;
}

/**
 * Process the next input event.
 */
static int32_t handle_input(struct android_app* app, AInputEvent* event) {
    struct activity_state* activity = (struct activity_state*)app->userData;
    if (AInputEvent_getType(event) == AINPUT_EVENT_TYPE_MOTION) {
        activity->savedState.x = AMotionEvent_getX(event, 0);
        activity->savedState.y = AMotionEvent_getY(event, 0);
        draw_frame(activity);
        return 1;

    }
    return 0;
}

/**
 * Process the next main command.
 */
static void handle_lifecycle_cmd(struct android_app* app, int32_t cmd) {
    struct activity_state* activity = (struct activity_state*)app->userData;
    switch (cmd) {
        case APP_CMD_SAVE_STATE:
            // The system has asked us to save our current state.  Do so.
        	activity->androidApp->savedState = malloc(sizeof(struct saved_state));
            *((struct saved_state*)activity->androidApp->savedState) = activity->savedState;
            activity->androidApp->savedStateSize = sizeof(struct saved_state);
            break;
        case APP_CMD_INIT_WINDOW:
            // activity window shown, init display
            if (activity->androidApp->window != NULL) {
                init_display(activity);
                draw_frame(activity);
            }
            break;
        case APP_CMD_TERM_WINDOW:
            // activity window closed, stop egl
            close_display(activity);
            break;
        case APP_CMD_INPUT_CHANGED:

        	break;
        case APP_CMD_START:
        	//activity onStart event
        	LOGI("nativeActivity: onStart");
        	android_app_pre_exec_cmd(app, cmd);
        	break;
        case APP_CMD_RESUME:
        	//activity onResume event
        	LOGI("nativeActivity: onResume");
        	android_app_pre_exec_cmd(app, cmd);
        	break;
        case APP_CMD_PAUSE:
        	//activity onPause event
        	LOGI("nativeActivity: onPause");
        	android_app_pre_exec_cmd(app, cmd);
        	break;
       case APP_CMD_STOP:
    	   //activity onStop event
    	   LOGI("nativeActivity: onStop");
    	   android_app_pre_exec_cmd(app, cmd);
    	   break;

       case APP_CMD_DESTROY:
    	   //activity onDestroy event
    	   LOGI("nativeActivity: onDestroy");
    	   android_app_pre_exec_cmd(app, cmd);
    	   break;
    }
}


void android_main(struct android_app* anroidApp) {

	struct activity_state activity;

	// Make sure glue isn't stripped.
	app_dummy();

	memset(&activity, 0, sizeof(activity));
	anroidApp->userData = &activity;
	anroidApp->onAppCmd = handle_lifecycle_cmd;
	anroidApp->onInputEvent = handle_input;
	activity.androidApp = anroidApp;

	LOGI("starting");

	if (anroidApp->savedState != NULL) {
		// We are starting with a previous saved state; restore from it.
		activity.savedState = *(struct saved_state*)anroidApp->savedState;
	}

    // loop waiting for stuff to do.

    while (1) {
        // Read all pending events.
        int ident;
        int events;
        struct android_poll_source* source;

        // wait for events
		while ((ident=ALooper_pollAll(-1, NULL, &events,(void**)&source)) >= 0) {

			// Process this event.
			if (source != NULL) {
				source->process(anroidApp, source);
			}

			// Check if the app is exited
			if (anroidApp->destroyRequested != 0) {
				close_display(&activity);
				return;
			}

		}

    }

}
