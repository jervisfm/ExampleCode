
#include <string.h>
#include <jni.h>


jint factorial(jint n){
if(n == 1){
	return 1;
	}
return factorial(n-1)*n;
}


jint
Java_com_cookbook_advance_ndk_NDK_factorial( JNIEnv* env,
                                                  jobject thiz, jint n )
{
    return factorial(n);
}

