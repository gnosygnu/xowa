/*!
 * VisualEditor ContentEditable MWGraphNode class.
 *
 * @license The MIT License (MIT); see LICENSE.txt
 */

/**
 * ContentEditable MediaWiki graph node.
 *
 * @class
 * @extends ve.ce.MWBlockExtensionNode
 * @mixins ve.ce.MWResizableNode
 *
 * @constructor
 * @param {ve.dm.MWGraphNode} model Model to observe
 * @param {Object} [config] Configuration options
 */
ve.ce.MWGraphNode = function VeCeMWGraphNode( model, config ) {
	this.$graph = $( '<div>' ).addClass( 'mw-graph' );
	this.$plot = $( '<div>' ).addClass( 've-ce-mwGraphNode-plot' );

	// Parent constructor
	ve.ce.MWGraphNode.super.apply( this, arguments );

	// Mixin constructors
	ve.ce.MWResizableNode.call( this, this.$plot, config );

	this.$element
		.addClass( 'mw-graph-container' )
		.append( this.$graph );

	this.showHandles( [ 'se' ] );
};

/* Inheritance */

OO.inheritClass( ve.ce.MWGraphNode, ve.ce.MWBlockExtensionNode );

// Need to mix in the base class as well
OO.mixinClass( ve.ce.MWGraphNode, ve.ce.ResizableNode );

OO.mixinClass( ve.ce.MWGraphNode, ve.ce.MWResizableNode );

/* Static Properties */

ve.ce.MWGraphNode.static.name = 'mwGraph';

ve.ce.MWGraphNode.static.primaryCommandName = 'graph';

ve.ce.MWGraphNode.static.tagName = 'div';

/* Static Methods */

/**
 * Attempt to render the graph through Vega.
 *
 * @param {Object} spec The graph spec
 * @param {HTMLElement} element Element to render the graph in
 * @return {jQuery.Promise} Promise that resolves when the graph is rendered.
 * Promise is rejected with an error message key if there was a problem rendering the graph.
 */
ve.ce.MWGraphNode.static.vegaParseSpec = function ( spec, element ) {
	var deferred = $.Deferred(),
		node = this,
		canvasNode, view;

	// Check if the spec is currently valid
	if ( ve.isEmptyObject( spec ) ) {
		deferred.reject( 'graph-ve-no-spec' );
	} else if ( !ve.dm.MWGraphModel.static.specHasData( spec ) ) {
		deferred.reject( 'graph-ve-empty-graph' );
	} else {
		vg.parse.spec( spec, function ( chart ) {
			try {
				view = chart( { el: element } ).update();

				// HACK: If canvas is blank, this means Vega didn't render properly.
				// Once Vega allows for proper rendering validation, this should be
				// swapped for a validation check.
				canvasNode = element.children[ 0 ].children[ 0 ];
				if ( node.isCanvasBlank( canvasNode ) ) {
					deferred.reject( 'graph-ve-vega-error-no-render' );
				} else {
					deferred.resolve( view );
				}

			} catch ( err ) {
				deferred.reject( 'graph-ve-vega-error' );
			}
		} );
	}

	return deferred.promise();
};

/**
 * Check if a canvas is blank
 *
 * @author Austin Brunkhorst http://stackoverflow.com/a/17386803/2055594
 * @param {HTMLElement} canvas The canvas to Check
 * @return {boolean} The canvas is blank
 */
ve.ce.MWGraphNode.static.isCanvasBlank = function ( canvas ) {
	var blank = document.createElement( 'canvas' );

	blank.width = canvas.width;
	blank.height = canvas.height;

	return canvas.toDataURL() === blank.toDataURL();
};

/* Methods */

/**
 * Render a Vega graph inside the node
 */
ve.ce.MWGraphNode.prototype.update = function () {
	var node = this;

	// Clear element
	this.$graph.empty();

	this.$element.toggleClass( 'mw-graph-vega1', this.getModel().isGraphLegacy() );

	mw.loader.using( 'ext.graph.vega2' ).done( function () {
		node.$plot.detach();

		node.constructor.static.vegaParseSpec( node.getModel().getSpec(), node.$graph[ 0 ] ).then(
			function ( view ) {
				// HACK: We need to know which padding values Vega computes in case
				// of automatic padding, but it isn't properly exposed in the view
				node.$graph.append( node.$plot );
				// eslint-disable-next-line no-underscore-dangle
				node.$plot.css( view._padding );

				node.calculateHighlights();
			},
			function ( failMessageKey ) {
				node.$graph.text( ve.msg( failMessageKey ) );
			}
		);
	} );
};

/**
 * @inheritdoc
 */
ve.ce.MWGraphNode.prototype.getAttributeChanges = function ( width, height ) {
	var attrChanges = {},
		newSpec = ve.dm.MWGraphModel.static.updateSpec( this.getModel().getSpec(), {
			width: width,
			height: height
		} );

	ve.setProp( attrChanges, 'mw', 'body', 'extsrc', JSON.stringify( newSpec ) );

	return attrChanges;
};

/**
 * @inheritdoc
 */
ve.ce.MWGraphNode.prototype.getFocusableElement = function () {
	return this.$graph;
};

/* Registration */

ve.ce.nodeFactory.register( ve.ce.MWGraphNode );
