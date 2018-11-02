(function (xo) {
  xo.fulltext_indexer = new function() {
    this.send = function(proc, args) {
      try {xo.server.send_by_bridge('xowa.wiki.fulltext.indexer', proc, args);}
      catch (err) {alert(err);}
    }
    this.index = function() {
      try {
        var data = 
        { wikis:                  xo.elem.get_val_or_null('wikis_box')
        , ns_ids:                 xo.elem.get_val_or_null('ns_ids_box')
        , idx_opt:                xo.elem.selectbox__selected_get("idx_opt")
        };
        xo.fulltext_indexer.send('index', data);
        document.getElementById('index_btn').focus();
      } catch (err) {
        alert(err);
      }
    }
    this.index_keydown = function(e) {
      if(e.keyCode === 13){
        e.preventDefault(); // Ensure it is only this code that runs
        xo.fulltext_indexer.index();
      }      
    }
    this.status__note__recv = function(msg) {return xo.fulltext_indexer.status__note(JSON.parse(msg));}
    this.status__note = function(msg) {
      try {
        var note = msg.note;
        
        xowa.js.doc.elem_append_above('note_last_div'
        , "<div style='width:100%'>"
        + "  <div>"
        + note
        + "  </div>"
        + "</div>"
        );
      } catch (err) {
        alert(err);
      }
      return true;
    }
    this.status__prog__recv = function(msg) {return xo.fulltext_indexer.status__prog(JSON.parse(msg));}
    this.status__prog = function(msg) {
      try {
        var prog = msg.prog;

        var prog_div = document.getElementById('prog_div');
        prog_div.textContent = prog;
      } catch (err) {
        alert(err);
      }
      return true;
    }
  }
}(window.xo = window.xo || {}));
xo.notify.elem_anchor = '#main_div';
document.getElementById("wikis_box").focus();
xo.elem.selectbox__selected_set('idx_opt');
