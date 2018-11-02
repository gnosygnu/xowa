// NOTE: edited from original to use local json libraries; DATE:2015-09-05
(function($){
  if (window.xtn__graph__exec == null) {
    window.xtn__graph__exec = graph_exec;
  }
  /*
  function graph_exec() {
    var $content = $('#content');
    $content.find( '.mw-graph.mw-graph-always' ).each( function () {
      console.log('found .mw-graph.mw-graph-always');
    });
    $content.find( '.mw-graph' ).each( function () {
      console.log('found .mw-graph');
    });
    $content.find( '.mw-graph' ).each( function () {
  //	$content.find( '.mw-wiki-graph' ).each( function () {
  //	$content.find( '.mw-graph' ).each( function () {
      var graphId = $( this ).data( 'graph-id' );
      var elem = this;
      var spec = JSON.parse(htmlDecode($(this).html()));
      $(this).html('');
      vg.parse.spec
      ( spec, function(chart) 
        {
          if (chart) {
            chart({ el: elem}).update();
          }
        }
      );
    });
  }
  */
	window.drawVegaGraph = function ( elem, data, callback ) {
		vg.parse.spec( data, function ( error, chart ) {
			if ( !error ) {
				chart( { el: elem } ).update();
			}
			if ( callback ) {
				callback( error );
			}
		} );
	};
  function graph_exec() {
    var $content = $('#content');

		var specs = {}; // mw.config.get( 'wgGraphSpecs' );
		if ( !specs ) {
			return;
		}
		$content.find( '.mw-graph,.mw-graph-always' ).each( function () {
//			var graphId = $( this ).data( 'graph-id' );
//			if ( !specs.hasOwnProperty( graphId ) ) {
//				mw.log.warn( graphId );
//			} else {
        var elem = this;
        var spec = JSON.parse(htmlDecode($(this).html()));
        $(this).html('');
				window.drawVegaGraph( this, spec, function ( error ) {
//				window.drawVegaGraph( this, specs[ graphId ], function ( error ) {
					if ( error ) {
//						mw.log.warn( error );
					}
				} );
//			}
		} );
  }
})(jQuery);

function htmlDecode(input)
{
  var doc = new DOMParser().parseFromString(input, "text/html");
  return doc.documentElement.textContent;
}