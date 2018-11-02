(function (xo) {
  xo.app_updater = new function() {
    var progbar = new xo.gui.progbars.Progbar('pbar-row', 'pbar-txt', 'pbar-bar');
    this.update = function(version_name) {
      try {
        xo.notify.elem_anchor = '#main_body';
        progbar.show();
        xo.server.send_by_bridge('app.updater', 'install', {version: version_name});
      } catch (e) {
        alert(e);
      }
    };
    this.skip = function(version_name) {
      try {
        xo.server.send_by_bridge('app.updater', 'skip', {version: version_name});
      } catch (e) {
        alert(e);
      }
    };
    this.download__prog = function(msg) {
      try {
        var args = JSON.parse(msg);
        if (args.done) {
          progbar.write('done', 100);
          return true;
        }

        progbar.write_by_notify(args.task_type + ' app update', args.prog_data_cur, args.prog_data_end, args.prog_rate);

        return true;
      } catch (e) {
        alert(e);
      }
    };
    this.write_status = function(msg) {
      try {
        var args = JSON.parse(msg);
        progbar.write_by_notify(args.msg);
      } catch (e) {
        alert(e);
      }
      return true;
    };
  }
}(window.xo = window.xo || {}));
