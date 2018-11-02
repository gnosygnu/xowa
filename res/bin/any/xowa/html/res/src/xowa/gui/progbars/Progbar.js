Namespace_.add("xo.gui.progbars");

xo.gui.progbars.Progbar = function(row_id, txt_id, bar_id) {
  this.row_id = row_id;
  this.txt_id = txt_id;
  this.bar_id = bar_id;
}

var proto = xo.gui.progbars.Progbar.prototype;
proto.show = function() {
  document.getElementById(this.row_id).style.display = 'inline-block';  
}
proto.write = function(html, width) {
  try {
    document.getElementById(this.txt_id).innerHTML = html;
    document.getElementById(this.bar_id).style.width = width + '%';
  } catch (e) {
    alert(e);    
  }
}
proto.write_by_notify = function(msg, data_cur, data_end, rate) {
  try {
    // calc vars for msg
    var time_til = (data_end - data_cur) / rate;
    var time_til_str = xo.time.to_dhms(Math.ceil(time_til));
    var pct = data_cur / data_end;
    var prog_msg = this.make_prog_msg(msg, (pct * 100), time_til_str, data_cur, data_end);

    // update gui
    this.write(prog_msg, pct * 100);
  } catch (e) {
    alert(e);
  }
};

// private
proto.make_prog_msg = function(name, percent, time_til_str, data_cur, data_end) {
  return String_.format
  ( "{0}: {1}% &middot; {2} &middot; {3} / {4}"
  , name, (percent | 0)
  , time_til_str
  , xo.iosize.to_str(data_cur), xo.iosize.to_str(data_end)
  );
};
proto = null;
