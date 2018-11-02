/*!
 * VisualEditor DataModel MWGraphModel class.
 *
 * @license The MIT License (MIT); see LICENSE.txt
 */

/**
 * MediaWiki graph model.
 *
 * @class
 * @mixins OO.EventEmitter
 *
 * @constructor
 * @param {Object} [spec] The Vega specification as a JSON object
 */
ve.dm.MWGraphModel = function VeDmMWGraphModel( spec ) {
	// Mixin constructors
	OO.EventEmitter.call( this );

	// Properties
	this.spec = spec || {};
	this.originalSpec = ve.copy( this.spec );

	this.cachedPadding = ve.copy( this.spec.padding ) || this.getDefaultPaddingObject();
};

/* Inheritance */

OO.mixinClass( ve.dm.MWGraphModel, OO.EventEmitter );

/* Static Members */

ve.dm.MWGraphModel.static.defaultPadding = 30;

ve.dm.MWGraphModel.static.minDimensions = {
	width: 60,
	height: 60
};

ve.dm.MWGraphModel.static.graphConfigs = {
	area: {
		mark: {
			type: 'area',
			properties: {
				enter: {
					fill: { value: 'steelblue' },
					interpolate: { value: 'monotone' },
					stroke: undefined,
					strokeWidth: undefined,
					width: undefined
				}
			}
		},
		scale: {
			name: 'x',
			type: 'linear'
		},
		fields: [
			'x',
			'y'
		]
	},

	bar: {
		mark: {
			type: 'rect',
			properties: {
				enter: {
					fill: { value: 'steelblue' },
					interpolate: undefined,
					stroke: undefined,
					strokeWidth: undefined,
					// HACK: Boolean values set to true need to be wrapped
					// in strings until T118883 is resolved
					width: { scale: 'x', band: 'true', offset: -1 }
				}
			}
		},
		scale: {
			name: 'x',
			type: 'ordinal'
		},
		fields: [
			'x',
			'y'
		]
	},

	line: {
		mark: {
			type: 'line',
			properties: {
				enter: {
					fill: undefined,
					interpolate: { value: 'monotone' },
					stroke: { value: 'steelblue' },
					strokeWidth: { value: 3 },
					width: undefined
				}
			}
		},
		scale: {
			name: 'x',
			type: 'linear'
		},
		fields: [
			'x',
			'y'
		]
	}
};

/* Events */

/**
 * @event specChange
 *
 * Change when the JSON specification is updated
 *
 * @param {Object} The new specification
 */

/* Static Methods */

/**
 * Updates a spec with new parameters.
 *
 * @param {Object} spec The spec to update
 * @param {Object} params The new params to update. Properties set to undefined will be removed from the spec.
 * @return {Object} The new spec
 */
ve.dm.MWGraphModel.static.updateSpec = function ( spec, params ) {
	var undefinedProperty,
		undefinedProperties = ve.dm.MWGraphModel.static.getUndefinedProperties( params ),
		i;

	// Remove undefined properties from spec
	for ( i = 0; i < undefinedProperties.length; i++ ) {
		undefinedProperty = undefinedProperties[ i ].split( '.' );
		ve.dm.MWGraphModel.static.removeProperty( spec, $.extend( [], undefinedProperty ) );
		ve.dm.MWGraphModel.static.removeProperty( params, $.extend( [], undefinedProperty ) );
	}

	// Extend remaining properties
	spec = $.extend( true, {}, spec, params );

	return spec;
};

/**
 * Recursively gets all the keys to properties set to undefined in a JSON object
 *
 * @author Based on the work on Artyom Neustroev at http://stackoverflow.com/a/15690816/2055594
 * @private
 * @param {Object} obj The object to iterate
 * @param {string} [stack] The parent property of the root property of obj. Used internally for recursion.
 * @param {string[]} [list] The list of properties to return. Used internally for recursion.
 * @return {string[]} The list of properties to return.
 */
ve.dm.MWGraphModel.static.getUndefinedProperties = function ( obj, stack, list ) {
	var property;

	list = list || [];

	// Append . to the stack if it's defined
	stack = ( stack === undefined ) ? '' : stack + '.';

	for ( property in obj ) {
		if ( obj.hasOwnProperty( property ) ) {
			if ( $.type( obj[ property ] ) === 'object' || $.type( obj[ property ] ) === 'array' ) {
				ve.dm.MWGraphModel.static.getUndefinedProperties( obj[ property ], stack + property, list );
			} else if ( obj[ property ] === undefined ) {
				list.push( stack + property );
			}
		}
	}

	return list;
};

/**
 * Removes a nested property from an object
 *
 * @param {Object} obj The object
 * @param {Array} prop The path of the property to remove
 */
ve.dm.MWGraphModel.static.removeProperty = function ( obj, prop ) {
	var firstProp = prop.shift();

	try {
		if ( prop.length > 0 ) {
			ve.dm.MWGraphModel.static.removeProperty( obj[ firstProp ], prop );
		} else {
			if ( $.type( obj ) === 'array' ) {
				obj.splice( parseInt( firstProp ), 1 );
			} else {
				delete obj[ firstProp ];
			}
		}
	} catch ( err ) {
		// We don't need to bubble errors here since hitting a missing property
		// will not exist anyway in the object anyway
	}
};

/**
 * Check if a spec currently has something in its dataset
 *
 * @param {Object} spec The spec
 * @return {boolean} The spec has some data in its dataset
 */
ve.dm.MWGraphModel.static.specHasData = function ( spec ) {
	// FIXME: Support multiple pipelines
	return !!spec.data[ 0 ].values.length;
};

/* Methods */

/**
 * Switch the graph to a different type
 *
 * @param {string} type Desired graph type. Can be either area, line or bar.
 * @fires specChange
 */
ve.dm.MWGraphModel.prototype.switchGraphType = function ( type ) {
	var params = {
		scales: [ ve.copy( this.constructor.static.graphConfigs[ type ].scale ) ],
		marks: [ ve.copy( this.constructor.static.graphConfigs[ type ].mark ) ]
	};

	this.updateSpec( params );

	this.emit( 'specChange', this.spec );
};

/**
 * Apply changes to the node
 *
 * @param {ve.dm.MWGraphNode} node The node to be modified
 * @param {ve.dm.Surface} surfaceModel The surface model for the document
 */
ve.dm.MWGraphModel.prototype.applyChanges = function ( node, surfaceModel ) {
	var mwData = ve.copy( node.getAttribute( 'mw' ) );

	// Send transaction
	mwData.body.extsrc = this.getSpecString();
	surfaceModel.change(
		ve.dm.TransactionBuilder.static.newFromAttributeChanges(
			surfaceModel.getDocument(),
			node.getOffset(),
			{ mw: mwData }
		)
	);
	surfaceModel.applyStaging();
};

/**
 * Update the spec with new parameters
 *
 * @param {Object} params The new parameters to be updated in the spec
 * @fires specChange
 */
ve.dm.MWGraphModel.prototype.updateSpec = function ( params ) {
	var updatedSpec = ve.dm.MWGraphModel.static.updateSpec( $.extend( true, {}, this.spec ), params );

	// Only emit a change event if the spec really changed
	if ( !OO.compare( this.spec, updatedSpec ) ) {
		this.spec = updatedSpec;
		this.emit( 'specChange', this.spec );
	}
};

/**
 * Sets and validates the specification from a stringified version
 *
 * @param {string} str The new specification string
 * @fires specChange
 */
ve.dm.MWGraphModel.prototype.setSpecFromString = function ( str ) {
	var newSpec = ve.dm.MWGraphNode.static.parseSpecString( str );

	// Only apply changes if the new spec is valid JSON and if the
	// spec truly was modified
	if ( !OO.compare( this.spec, newSpec ) ) {
		this.spec = newSpec;
		this.emit( 'specChange', this.spec );
	}
};

/**
 * Get the specification
 *
 * @return {Object} The specification
 */
ve.dm.MWGraphModel.prototype.getSpec = function () {
	return this.spec;
};

/**
 * Get the stringified specification
 *
 * @return {string} The specification string
 */
ve.dm.MWGraphModel.prototype.getSpecString = function () {
	return ve.dm.MWGraphNode.static.stringifySpec( this.spec );
};

/**
 * Get the original stringified specificiation
 *
 * @return {string} The original JSON string specification
 */
ve.dm.MWGraphModel.prototype.getOriginalSpecString = function () {
	return ve.dm.MWGraphNode.static.stringifySpec( this.originalSpec );
};

/**
 * Get the graph type
 *
 * @return {string} The graph type
 */
ve.dm.MWGraphModel.prototype.getGraphType = function () {
	var markType = this.spec.marks[ 0 ].type;

	switch ( markType ) {
		case 'area':
			return 'area';
		case 'rect':
			return 'bar';
		case 'line':
			return 'line';
		default:
			return 'unknown';
	}
};

/**
 * Get graph size
 *
 * @return {Object} The graph width and height
 */
ve.dm.MWGraphModel.prototype.getSize = function () {
	return {
		width: this.spec.width,
		height: this.spec.height
	};
};

/**
 * Set the graph width
 *
 * @param {number} value The new width
 * @fires specChange
 */
ve.dm.MWGraphModel.prototype.setWidth = function ( value ) {
	this.spec.width = value;

	this.emit( 'specChange', this.spec );
};

/**
 * Set the graph height
 *
 * @param {number} value The new height
 * @fires specChange
 */
ve.dm.MWGraphModel.prototype.setHeight = function ( value ) {
	this.spec.height = value;

	this.emit( 'specChange', this.spec );
};

/**
 * Get the padding values of the graph
 *
 * @return {Object} The paddings
 */
ve.dm.MWGraphModel.prototype.getPaddingObject = function () {
	return this.spec.padding;
};

/**
 * Return the default padding
 *
 * @return {Object} The default padding values
 */
ve.dm.MWGraphModel.prototype.getDefaultPaddingObject = function () {
	var i,
		indexes = [ 'top', 'bottom', 'left', 'right' ],
		paddingObj = {};

	for ( i = 0; i < indexes.length; i++ ) {
		paddingObj[ indexes[ i ] ] = ve.dm.MWGraphModel.static.defaultPadding;
	}

	return paddingObj;
};

/**
 * Set a padding value
 *
 * @param {string} index The index to change. Can be either top, right, bottom or right
 * @param {number} value The new value
 * @fires specChange
 */
ve.dm.MWGraphModel.prototype.setPadding = function ( index, value ) {
	if ( this.isPaddingAutomatic() ) {
		this.spec.padding = this.getDefaultPaddingObject();
	}

	this.spec.padding[ index ] = value;

	this.emit( 'specChange', this.spec );
};

/**
 * Toggles automatic and manual padding modes
 *
 * @param {boolean} auto Padding is now automatic
 * @fires specChange
 */
ve.dm.MWGraphModel.prototype.setPaddingAuto = function ( auto ) {
	if ( auto ) {
		this.cachedPadding = ve.copy( this.spec.padding ) || this.getDefaultPaddingObject();
		ve.dm.MWGraphModel.static.removeProperty( this.spec, [ 'padding' ] );
	} else {
		this.spec.padding = ve.copy( this.cachedPadding );
	}

	this.emit( 'specChange', this.spec );
};

/**
 * Get the fields for a data pipeline
 *
 * @param {number} [id] The pipeline's id
 * @return {string[]} The fields for the pipeline
 */
ve.dm.MWGraphModel.prototype.getPipelineFields = function ( id ) {
	var firstEntry = ve.getProp( this.spec, 'data', id, 'values', 0 );

	// Get the fields directly from the pipeline data if the pipeline exists and
	// has data, otherwise default back on the fields intended for this graph type
	if ( firstEntry ) {
		return Object.keys( firstEntry );
	} else {
		return ve.dm.MWGraphModel.static.graphConfigs[ this.getGraphType() ].fields;
	}
};

/**
 * Get a data pipeline
 *
 * @param {number} [id] The pipeline's id
 * @return {Object} The data pipeline within the spec
 */
ve.dm.MWGraphModel.prototype.getPipeline = function ( id ) {
	return this.spec.data[ id ];
};

/**
 * Set the field value of an entry in a pipeline
 *
 * @param {number} [entry] ID of the entry
 * @param {string} [field] The field to change
 * @param {number} [value] The new value
 * @fires specChange
 */
ve.dm.MWGraphModel.prototype.setEntryField = function ( entry, field, value ) {
	if ( this.spec.data[ 0 ].values[ entry ] === undefined ) {
		this.spec.data[ 0 ].values[ entry ] = this.buildNewEntry( 0 );
	}
	this.spec.data[ 0 ].values[ entry ][ field ] = value;

	this.emit( 'specChange', this.spec );
};

/**
 * Builds and returns a new entry for a pipeline
 *
 * @private
 * @param {number} [pipelineId] The ID of the pipeline the entry is intended for
 * @return {Object} The new entry
 */
ve.dm.MWGraphModel.prototype.buildNewEntry = function ( pipelineId ) {
	var fields = this.getPipelineFields( pipelineId ),
		newEntry = {},
		i;

	for ( i = 0; i < fields.length; i++ ) {
		newEntry[ fields[ i ] ] = '';
	}

	return newEntry;
};

/**
 * Removes an entry from a pipeline
 *
 * @param {number} [index] The index of the entry to delete
 * @fires specChange
 */
ve.dm.MWGraphModel.prototype.removeEntry = function ( index ) {
	// FIXME: Support multiple pipelines
	this.spec.data[ 0 ].values.splice( index, 1 );

	this.emit( 'specChange', this.spec );
};

/**
 * Returns whether the current spec has been modified since the dialog was opened
 *
 * @return {boolean} The spec was changed
 */
ve.dm.MWGraphModel.prototype.hasBeenChanged = function () {
	return !OO.compare( this.spec, this.originalSpec );
};

/**
 * Returns whether the padding is set to be automatic or not
 *
 * @return {boolean} The padding is automatic
 */
ve.dm.MWGraphModel.prototype.isPaddingAutomatic = function () {
	return OO.compare( this.spec.padding, undefined );
};
