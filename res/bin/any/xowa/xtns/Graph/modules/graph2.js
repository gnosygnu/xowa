( function ( $, mw, vg ) {

	'use strict';
	/* global require */

  /*
	var VegaWrapper = require( 'mw-graph-shared' );

	// eslint-disable-next-line no-new
	new VegaWrapper( {
		datalib: vg.util,
		useXhr: true,
		isTrusted: true, //mw.config.get( 'wgGraphIsTrusted' ),
		domains: '', // mw.config.get( 'wgGraphAllowedDomains' ),
		domainMap: false,
		logger: function ( warning ) {
//			mw.log.warn( warning );
		},
		parseUrl: function ( opt ) {
			// Parse URL
			var uri = new mw.Uri( opt.url );
			// reduce confusion, only keep expected values
			if ( uri.port ) {
				uri.host += ':' + uri.port;
				delete uri.port;
			}
			// If url begins with   protocol:///...  mark it as having relative host
			if ( /^[a-z]+:\/\/\//.test( opt.url ) ) {
				uri.isRelativeHost = true;
			}
			if ( uri.protocol ) {
				// All other libs use trailing colon in the protocol field
				uri.protocol += ':';
			}
			// Node's path includes the query, whereas pathname is without the query
			// Standardizing on pathname
			uri.pathname = uri.path;
			delete uri.path;
			return uri;
		},
		formatUrl: function ( uri, opt ) {
			// Format URL back into a string
			// Revert path into pathname
			uri.path = uri.pathname;
			delete uri.pathname;

			if ( location.host.toLowerCase() === uri.host.toLowerCase() ) {
//				if ( !mw.config.get( 'wgGraphIsTrusted' ) ) {
					// Only send this header when hostname is the same.
					// This is broader than the same-origin policy,
					// but playing on the safer side.
//					opt.headers = { 'Treat-as-Untrusted': 1 };
//				}
			} else if ( opt.addCorsOrigin ) {
				// All CORS api calls require origin parameter.
				// It would be better to use location.origin,
				// but apparently it's not universal yet.
				uri.query.origin = location.protocol + '//' + location.host;
			}

			uri.protocol = VegaWrapper.removeColon( uri.protocol );

			return uri.toString();
		},
//		languageCode: mw.config.get( 'wgUserLanguage' )
	} );
*/
	/**
	 * Set up drawing canvas inside the given element and draw graph data
	 *
	 * @param {HTMLElement} element
	 * @param {Object|string} data graph spec
	 * @param {Function} [callback] function(error) called when drawing is done
	 */
	window.drawVegaGraph = function ( element, data, callback ) {
		vg.parse.spec( data, function ( error, chart ) {
			if ( !error ) {
				chart( { el: element } ).update();
			}
			if ( callback ) {
				callback( error );
			}
		} );
	};

//	mw.hook( 'wikipage.content' ).add( function ( $content ) {
    var $content = $('#content');

		var specs = {} // mw.config.get( 'wgGraphSpecs' );
		if ( !specs ) {
			return;
		}
		$content.find( '.mw-graph.mw-graph-always' ).each( function () {
			var graphId = $( this ).data( 'graph-id' );
			if ( !specs.hasOwnProperty( graphId ) ) {
//				mw.log.warn( graphId );
			} else {
				window.drawVegaGraph( this, specs[ graphId ], function ( error ) {
					if ( error ) {
	//					mw.log.warn( error );
					}
				} );
			}
		} );
//	} );

}( jQuery, null, vg ) );
