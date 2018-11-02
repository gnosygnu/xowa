/*!
 * VisualEditor DataModel MWGraphNode class.
 *
 * @license The MIT License (MIT); see LICENSE.txt
 */

/**
 * DataModel MediaWiki graph node.
 *
 * @class
 * @extends ve.dm.MWBlockExtensionNode
 * @mixins ve.dm.ResizableNode
 *
 * @constructor
 * @param {Object} [element]
 */
ve.dm.MWGraphNode = function VeDmMWGraphNode() {
	var mw, extsrc;

	// Parent constructor
	ve.dm.MWGraphNode.super.apply( this, arguments );

	// Mixin constructors
	ve.dm.ResizableNode.call( this );

	// Properties
	this.spec = null;

	// Events
	this.connect( this, {
		attributeChange: 'onAttributeChange'
	} );

	// Initialize specificiation
	mw = this.getAttribute( 'mw' );
	extsrc = ve.getProp( mw, 'body', 'extsrc' );

	if ( extsrc ) {
		this.setSpecFromString( extsrc );
	} else {
		this.setSpec( ve.dm.MWGraphNode.static.defaultSpec );
	}
};

/* Inheritance */

OO.inheritClass( ve.dm.MWGraphNode, ve.dm.MWBlockExtensionNode );
OO.mixinClass( ve.dm.MWGraphNode, ve.dm.ResizableNode );

/* Static Members */

ve.dm.MWGraphNode.static.name = 'mwGraph';

ve.dm.MWGraphNode.static.extensionName = 'graph';

ve.dm.MWGraphNode.static.defaultSpec = {
	version: 2,
	width: 400,
	height: 200,
	data: [
		{
			name: 'table',
			values: [
				{
					x: 0,
					y: 1
				},
				{
					x: 1,
					y: 3
				},
				{
					x: 2,
					y: 2
				},
				{
					x: 3,
					y: 4
				}
			]
		}
	],
	scales: [
		{
			name: 'x',
			type: 'linear',
			range: 'width',
			zero: false,
			domain: {
				data: 'table',
				field: 'x'
			}
		},
		{
			name: 'y',
			type: 'linear',
			range: 'height',
			nice: true,
			domain: {
				data: 'table',
				field: 'y'
			}
		}
	],
	axes: [
		{
			type: 'x',
			scale: 'x'
		},
		{
			type: 'y',
			scale: 'y'
		}
	],
	marks: [
		{
			type: 'area',
			from: {
				data: 'table'
			},
			properties: {
				enter: {
					x: {
						scale: 'x',
						field: 'x'
					},
					y: {
						scale: 'y',
						field: 'y'
					},
					y2: {
						scale: 'y',
						value: 0
					},
					fill: {
						value: 'steelblue'
					},
					interpolate: {
						value: 'monotone'
					}
				}
			}
		}
	]
};

/* Static Methods */

/**
 * Parses a spec string and returns its object representation.
 *
 * @param {string} str The spec string to validate. If the string is null or represents an empty object, the spec will be null.
 * @return {Object} The object specification. On a failed parsing, the object will be returned empty.
 */
ve.dm.MWGraphNode.static.parseSpecString = function ( str ) {
	var result;

	try {
		result = JSON.parse( str );

		// JSON.parse can return other types than Object, we don't want that
		// The error will be caught just below as this counts as a failed process
		if ( $.type( result ) !== 'object' ) {
			throw new Error();
		}

		return result;
	} catch ( err ) {
		return {};
	}
};

/**
 * Return the indented string representation of a spec.
 *
 * @param {Object} spec The object specificiation.
 * @return {string} The stringified version of the spec.
 */
ve.dm.MWGraphNode.static.stringifySpec = function ( spec ) {
	var result = JSON.stringify( spec, null, '\t' );
	return result || '';
};

/* Methods */

/**
 * @inheritdoc
 */
ve.dm.MWGraphNode.prototype.createScalable = function () {
	var width = ve.getProp( this.spec, 'width' ),
		height = ve.getProp( this.spec, 'height' );

	return new ve.dm.Scalable( {
		currentDimensions: {
			width: width,
			height: height
		},
		minDimensions: ve.dm.MWGraphModel.static.minDimensions,
		fixedRatio: false
	} );
};

/**
 * Get the specification string
 *
 * @return {string} The specification JSON string
 */
ve.dm.MWGraphNode.prototype.getSpecString = function () {
	return this.constructor.static.stringifySpec( this.spec );
};

/**
 * Get the parsed JSON specification
 *
 * @return {Object} The specification object
 */
ve.dm.MWGraphNode.prototype.getSpec = function () {
	return this.spec;
};

/**
 * Set the specificiation
 *
 * @param {Object} spec The new spec
 */
ve.dm.MWGraphNode.prototype.setSpec = function ( spec ) {
	// Consolidate all falsy values to an empty object for consistency
	this.spec = spec || {};
};

/**
 * Set the specification from a stringified version
 *
 * @param {string} str The new specification JSON string
 */
ve.dm.MWGraphNode.prototype.setSpecFromString = function ( str ) {
	this.setSpec( this.constructor.static.parseSpecString( str ) );
};

/**
 * React to node attribute changes
 *
 * @param {string} attributeName The attribute being updated
 * @param {Object} from The old value of the attribute
 * @param {Object} to The new value of the attribute
 */
ve.dm.MWGraphNode.prototype.onAttributeChange = function ( attributeName, from, to ) {
	if ( attributeName === 'mw' ) {
		this.setSpecFromString( to.body.extsrc );
	}
};

/**
 * Is this graph using a legacy version of Vega?
 *
 * @return {boolean}
 */
ve.dm.MWGraphNode.prototype.isGraphLegacy = function () {
	return !!( this.spec && this.spec.hasOwnProperty( 'version' ) && this.spec.version < 2 );
};

/* Registration */

ve.dm.modelRegistry.register( ve.dm.MWGraphNode );
