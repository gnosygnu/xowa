(function (xo) {
  xo.wiki_directory = new function() {
    this.send = function(proc, args) {
      try {
        xo.server.send_by_bridge('wiki.directory.list', proc, args);
      } catch (err) {
        alert(err);
      }
    }

    this.import_wiki = function() {
      try {
        // call xowa to launch file_dialog
        var url = xowa_exec('scripts_exec', 'app.gui.kit.ask_file("Please select an .xowa file", "*.xowa");');
        if (url == null || url == '') return; // nothing selected; exit
        
        xo.notify.elem_anchor = '#main_body';
        xo.wiki_directory.send('import_wiki', {'url':url});
      } catch (err) {
        alert(err);
      }
    }    
  }
}(window.xo = window.xo || {}));
