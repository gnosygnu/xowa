xo.logs = xo.logs || {};
xo.logs.logger = function(type_name) {
  this.type_name = type_name;
  this.warn = function() {this.write("WARN", arguments);};
  this.info = function() {this.write("INFO", arguments);};
  
  this.write = function(mode, args) {
    var len = args.length;
    
    var msg = this.format(new Date(), "yyyy-MM-dd hh:mm:ss.SSS") + " " + mode + " [" + this.type_name + "] " + args[0];
    for (var i = 1; i < len; i++) {
      var prefix 
        = ((i % 2) == 1)
        ? "; "
        : "="
        ;
      msg += prefix + args[i];
    }
    console.log(msg);    
  }
  // REF:https://stackoverflow.com/questions/23593052/format-javascript-date-to-yyyy-mm-dd
  this.format = function(x, y) {
      var z = {
          M: x.getMonth() + 1,
          d: x.getDate(),
          h: x.getHours(),
          m: x.getMinutes(),
          s: x.getSeconds()
      };
      y = y.replace(/(M+|d+|h+|m+|s+)/g, function(v) {
          return ((v.length > 1 ? "0" : "") + eval('z.' + v.slice(-1))).slice(-2)
      });
      
      y = y.replace(/(S+)/g, function(v) {
          return x.getMilliseconds().toString().slice(-v.length)
      });

      return y.replace(/(y+)/g, function(v) {
          return x.getFullYear().toString().slice(-v.length)
      });
  }
};

