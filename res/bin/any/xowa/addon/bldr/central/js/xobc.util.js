(function (xo) {
  xo.time = new function() {
    this.to_dhms = function(val, opts) {
      var show_units = (opts && opts.show_units) ? opts.show_units : true;
      var max_places = (opts && opts.max_places) ? opts.max_places : 2;
      var rv = '';
      var suffix = '';
      var count = 0;
      for (var i = 0; i < 4; ++i) {
        // get factor and unit
        var factor = 0;
        var unit = '';
        switch(i) {
          case 0: unit = 'd'; factor = 86400; break;
          case 1: unit = 'h'; factor =  3600; break;
          case 2: unit = 'm'; factor =    60; break;
          case 3: unit = 's'; factor =     1; break;
        }
        // calc cur and update val; EX: 3690 -> cur:1,mod:90
        var cur = (val / factor) | 0;
        val %= factor;
        if (count == 0) {					    // no str yet
          if (    cur == 0            // cur is 0; EX: 3690 and factor = 86400 -> 0 days; don't write anything
              &&	i != 3)					    // unless it is the seconds place; need to handle "0 s" specifically
              continue;			         
          suffix = unit;					    // set suffix
          rv += cur;	                // write cur; note that this is not zero-padded
        }
        else {								        // str exists
          if (cur < 10) rv += '0';
          rv += cur;                  // write cur; note that this zero-padded; also, note will write "00" if cur == 0
        }
        if (++count == max_places) break;	// stop if max-places reached; EX: 86400 should write 1:00, not 1:00:00:00
        if (i != 3)							      // do not add ":" if seconds
          rv += ':'
      }
      if (show_units)	// add units; EX: " s" for seconds
        rv += ' ' + suffix;
      return rv;
    }
    /*
    console.log(to_dhms(1));
    console.log(to_dhms(30));
    console.log(to_dhms(60));
    console.log(to_dhms(600));
    console.log(to_dhms(3600));
    console.log(to_dhms(5025));
    console.log(to_dhms(86400));
    */
  }
  xo.iosize = new function() {
    this.to_str = function(val, opts) {
      if (!val) return '0 B'; // handle undefined
      var decimal_places = (opts && opts.decimal_places) ? opts.decimal_places : 2;      
      
      // init
			var unit_idx = 0;
			var mult = 1024;
			var cur_val = val;
			var cur_exp = 1;
			var nxt_exp = mult;
      
      // get 1024 mult; EX: 1512 -> 1024
			for (unit_idx = 0; unit_idx < 6; ++unit_idx) {
				if (cur_val < nxt_exp) break;
				cur_exp = nxt_exp;
				nxt_exp *= mult;
			}
      
      // calc integer / decimal values
			var int_val = (val / cur_exp) | 0;
			var dec_val = (val % cur_exp) | 0;
			if (decimal_places == 0) {  // if 0 decimal places, round up
				if (dec_val >= .5) ++int_val;
				dec_val = 0;
			}
			else {// else, calculate decimal value as integer; EX: 549 -> .512 -> 512
				var dec_factor = 0;
				switch (decimal_places) {
					case 1: dec_factor =   10; break;
					case 2: dec_factor =  100; break;
					default:
					case 3: dec_factor = 1000; break;
				}
				dec_val = ((dec_val * dec_factor) / cur_exp) | 0;
			}
      
      // calc unit_str
			var unit_str = "";
			switch (unit_idx) {
				case 0:		unit_str = " b";  break;
				case 1:		unit_str = " kb"; break;
				case 2:		unit_str = " mb"; break;
				case 3:		unit_str = " gb"; break;
				case 4:		unit_str = " pb"; break;
				case 5: 
				default:	unit_str = " eb"; break;
			}
      
      // build string
      var rv = int_val;
			if (decimal_places > 0 && unit_idx != 0) {
				rv += '.' + dec_val;
			}
      rv += unit_str;
      return rv;
    }
    /*
    console.log(to_str((1024 * 1024) + 536871, {decimal_places : 1}));
    console.log(to_str((1024 * 1024) + 536871, {decimal_places : 2}));
    console.log(to_str((1024 * 1024) + 536871, {decimal_places : 3}));
    */
  }
}(window.xo = window.xo || {}));
