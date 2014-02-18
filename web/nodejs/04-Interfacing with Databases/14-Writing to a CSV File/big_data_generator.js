var c = 0;
while (c < 500000) {
  var i, rec = '', delim;
  for (i = 0; i<= 7; i++) {
    delim = (i > 0) ? ',' : '';
    rec += delim + '"' +(Math.random() * Math.random()).toString(16).replace('.', '') + '"';
  };
  c += 1;
    console.log(rec);
}
