/*!
 * VisualEditor MWGraphNode tests.
 */

QUnit.module( 'ext.graph.visualEditor' );

( function () {
	'use strict';

	/* Sample specs */

	var sampleSpecs = {
		areaGraph: {
			version: 2,
			width: 500,
			height: 200,
			padding: {
				top: 10,
				left: 30,
				bottom: 30,
				right: 10
			},
			data: [
				{
					name: 'table',
					values: [
						{ x: 0, y: 28 },
						{ x: 1, y: 43 },
						{ x: 2, y: 81 },
						{ x: 3, y: 19 }
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
							interpolate: {
								value: 'monotone'
							},
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
							}
						}
					}
				}
			]
		},

		stackedAreaGraph: {
			version: 2,
			width: 500,
			height: 200,
			padding: {
				top: 10,
				left: 30,
				bottom: 30,
				right: 10
			},
			data: [
				{
					name: 'table',
					values: [
						{ x: 0, y: 28, c: 0 },
						{ x: 0, y: 55, c: 1 },
						{ x: 1, y: 43, c: 0 },
						{ x: 1, y: 91, c: 1 },
						{ x: 2, y: 81, c: 0 },
						{ x: 2, y: 53, c: 1 },
						{ x: 3, y: 19, c: 0 },
						{ x: 3, y: 87, c: 1 },
						{ x: 4, y: 52, c: 0 },
						{ x: 4, y: 48, c: 1 },
						{ x: 5, y: 24, c: 0 },
						{ x: 5, y: 49, c: 1 },
						{ x: 6, y: 87, c: 0 },
						{ x: 6, y: 66, c: 1 },
						{ x: 7, y: 17, c: 0 },
						{ x: 7, y: 27, c: 1 },
						{ x: 8, y: 68, c: 0 },
						{ x: 8, y: 16, c: 1 },
						{ x: 9, y: 49, c: 0 },
						{ x: 9, y: 15, c: 1 }
					]
				},
				{
					name: 'stats',
					source: 'table',
					transform: [
						{
							type: 'facet',
							keys: [
								'x'
							]
						},
						{
							type: 'stats',
							value: 'y'
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
						data: 'stats',
						field: 'sum'
					}
				},
				{
					name: 'color',
					type: 'ordinal',
					range: 'category10'
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
					type: 'group',
					from: {
						data: 'table',
						transform: [
							{
								type: 'facet',
								keys: [
									'c'
								]
							},
							{
								type: 'stack',
								point: 'x',
								height: 'y'
							}
						]
					},
					marks: [
						{
							type: 'area',
							properties: {
								enter: {
									interpolate: {
										value: 'monotone'
									},
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
										field: 'y2'
									},
									fill: {
										scale: 'color',
										field: 'c'
									}
								},
								update: {
									fillOpacity: {
										value: 1
									}
								},
								hover: {
									fillOpacity: {
										value: 0.5
									}
								}
							}
						}
					]
				}
			]
		},

		invalidAxesBarGraph: {
			version: 2,
			width: 500,
			height: 200,
			padding: {
				top: 10,
				left: 30,
				bottom: 30,
				right: 10
			},
			data: [
				{
					name: 'table',
					values: [
						{ x: 0, y: 28 },
						{ x: 1, y: 43 },
						{ x: 2, y: 81 },
						{ x: 3, y: 19 }
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
					scale: 'z'
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
							interpolate: {
								value: 'monotone'
							},
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
							}
						}
					}
				}
			]
		}
	};

	/* Tests */

	QUnit.test( 've.dm.MWGraphNode', function ( assert ) {
		var node = new ve.dm.MWGraphNode(),
			specString = JSON.stringify( sampleSpecs.areaGraph );

		assert.deepEqual( node.getSpec(), ve.dm.MWGraphNode.static.defaultSpec, 'MWGraphNode spec is initialized to the default spec' );

		node.setSpecFromString( specString );
		assert.deepEqual( node.getSpec(), sampleSpecs.areaGraph, 'Basic valid spec is parsed' );

		node.setSpecFromString( 'invalid JSON string' );
		assert.deepEqual( node.getSpec(), {}, 'Setting an invalid JSON resets the spec to an empty object' );

		node.setSpec( sampleSpecs.stackedAreaGraph );
		assert.deepEqual( node.getSpec(), sampleSpecs.stackedAreaGraph, 'Setting the spec by object' );

		node.setSpec( null );
		assert.deepEqual( node.getSpec(), {}, 'Setting a null spec resets the spec to an empty object' );
	} );

	QUnit.test( 've.ce.MWGraphNode', function ( assert ) {
		var view = ve.test.utils.createSurfaceViewFromHtml(
				'<div typeof="mw:Extension/graph"></div>'
			),
			documentNode = view.getDocument().getDocumentNode(),
			node = documentNode.children[ 0 ];

		assert.equal( node.type, 'mwGraph', 'Parsoid HTML graphs are properly recognized as graph nodes' );
	} );

	QUnit.test( 've.ce.MWGraphNode.static', function ( assert ) {
		var testElement = document.createElement( 'div' ),
			renderValidTest = assert.async(),
			renderInvalidTest = assert.async();

		$( '#qunit-fixture' ).append( testElement );

		ve.ce.MWGraphNode.static.vegaParseSpec( sampleSpecs.areaGraph, testElement ).always(
			function () {
				assert.ok( this.state() === 'resolved', 'Simple graph gets rendered correctly' );
				renderValidTest();
			}
		);

		ve.ce.MWGraphNode.static.vegaParseSpec( sampleSpecs.invalidAxesBarGraph, testElement ).always(
			function ( failMessageKey ) {
				assert.ok( failMessageKey === 'graph-ve-vega-error', 'Invalid graph triggers an error at rendering' );
				renderInvalidTest();
			}
		);
	} );

	QUnit.test( 've.dm.MWGraphModel', function ( assert ) {
		var model = new ve.dm.MWGraphModel( sampleSpecs.areaGraph ),
			updateSpecRemoval = {
				marks: undefined,
				scales: undefined,
				padding: { top: 50 },
				axes: [
					{ type: 'z' }
				]
			},
			areaGraphRemovalExpected = {
				version: 2,
				width: 500,
				height: 200,
				padding: {
					top: 50,
					left: 30,
					bottom: 30,
					right: 10
				},
				data: [
					{
						name: 'table',
						values: [
							{ x: 0, y: 28 },
							{ x: 1, y: 43 },
							{ x: 2, y: 81 },
							{ x: 3, y: 19 }
						]
					}
				],
				axes: [
					{
						type: 'z',
						scale: 'x'
					},
					{
						type: 'y',
						scale: 'y'
					}
				]
			};

		assert.equal( model.hasBeenChanged(), false, 'Model changes are correctly initialized' );

		model.setSpecFromString( 'invalid json string' );
		assert.equal( model.hasBeenChanged(), true, 'Model spec resets to an empty object when fed invalid data' );

		model.setSpecFromString( JSON.stringify( sampleSpecs.areaGraph, null, '\t' ) );
		assert.equal( model.hasBeenChanged(), false, 'Model doesn\'t throw false positives after applying no changes' );

		model.setSpecFromString( JSON.stringify( sampleSpecs.stackedAreaGraph ) );
		assert.equal( model.hasBeenChanged(), true, 'Model recognizes valid changes to spec' );

		model.setSpecFromString( JSON.stringify( sampleSpecs.areaGraph ) );
		model.updateSpec( updateSpecRemoval );
		assert.deepEqual( model.getSpec(), areaGraphRemovalExpected, 'Updating the spec and removing properties' );
	} );

	QUnit.test( 've.dm.MWGraphModel.static', function ( assert ) {
		var result,
			basicTestObj = {
				a: 3,
				b: undefined,
				c: {
					ca: undefined,
					cb: 'undefined'
				}
			},
			complexTestObj = {
				a: {
					aa: undefined,
					ab: 3,
					ac: [
						{
							ac0a: undefined,
							ac0b: 4
						},
						{
							ac1a: 'ac1a',
							ac1b: 5,
							ac1c: undefined
						}
					]
				},
				b: {
					a: undefined,
					b: undefined,
					c: 2
				},
				c: 3,
				d: undefined
			},
			undefinedPropertiesBasicExpected = [ 'b', 'c.ca' ],
			undefinedPropertiesComplexExpected = [ 'a.aa', 'a.ac.0.ac0a', 'a.ac.1.ac1c', 'b.a', 'b.b', 'd' ],
			removePropBasicExpected = {
				a: 3,
				b: undefined,
				c: {
					cb: 'undefined'
				}
			},
			removePropComplexExpected = {
				a: {
					aa: undefined,
					ab: 3,
					ac: [ {
						ac1b: 5,
						ac1c: undefined
					} ]
				},
				c: 3,
				d: undefined
			};

		result = ve.dm.MWGraphModel.static.getUndefinedProperties( basicTestObj );
		assert.deepEqual( result, undefinedPropertiesBasicExpected, 'Basic deep undefined property scan is successful' );

		result = ve.dm.MWGraphModel.static.getUndefinedProperties( complexTestObj );
		assert.deepEqual( result, undefinedPropertiesComplexExpected, 'Complex deep undefined property scan is successful' );

		result = ve.dm.MWGraphModel.static.removeProperty( basicTestObj, [ 'c', 'ca' ] );
		assert.deepEqual( basicTestObj, removePropBasicExpected, 'Basic nested property removal is successful' );

		ve.dm.MWGraphModel.static.removeProperty( complexTestObj, [ 'a', 'ac', '0' ] );
		ve.dm.MWGraphModel.static.removeProperty( complexTestObj, [ 'a', 'ac', '0', 'ac1a' ] );
		ve.dm.MWGraphModel.static.removeProperty( complexTestObj, [ 'b' ] );
		assert.deepEqual( complexTestObj, removePropComplexExpected, 'Complex nested property removal is successful' );

		assert.throws(
			ve.dm.MWGraphModel.static.removeProperty( complexTestObj, [ 'b' ] ),
			'Trying to delete an invalid property throws an error'
		);
	} );

	QUnit.test( 've.ui.TableWidget', function ( assert ) {
		var widgetA = new ve.ui.TableWidget( {
				rows: [
					{
						key: 'foo',
						label: 'Foo'
					},
					{
						key: 'bar',
						label: 'Bar'
					}
				],
				cols: [
					{
						label: 'A'
					},
					{
						label: 'B'
					}
				]
			} ),
			widgetB = new ve.ui.TableWidget( {
				rows: [
					{
						key: 'foo'
					},
					{
						key: 'bar'
					}
				],
				cols: [
					{}, {}, {}
				],
				data: [
					[ '11', '12', '13' ],
					[ '21', '22', '23' ]
				]
			} ),
			widgetAexpectedRowProps = {
				index: 1,
				key: 'bar',
				label: 'Bar'
			},
			widgetAexpectedInitialData = [
				[ '', '' ],
				[ '', '' ]
			],
			widgetAexpectedDataAfterValue = [
				[ '', '' ],
				[ '3', '' ]
			],
			widgetAexpectedDataAfterRowColumnInsertions = [
				[ '', '', '' ],
				[ 'a', 'b', 'c' ],
				[ '3', '', '' ]
			],
			widgetAexpectedDataAfterColumnRemoval = [
				[ '', '' ],
				[ 'b', 'c' ],
				[ '', '' ]
			];

		assert.deepEqual( widgetA.model.data, widgetAexpectedInitialData, 'Table data is initialized properly' );

		assert.deepEqual( widgetA.model.getRowProperties( 'bar' ), widgetAexpectedRowProps, 'Row props are returned successfully' );

		widgetA.setValue( 'bar', 0, '3' );
		assert.deepEqual( widgetA.model.data, widgetAexpectedDataAfterValue, 'Table data is modified successfully' );

		widgetA.insertColumn();
		widgetA.insertRow( [ 'a', 'b', 'c' ], 1, 'baz', 'Baz' );
		assert.deepEqual( widgetA.model.data, widgetAexpectedDataAfterRowColumnInsertions, 'Row and column are added successfully' );

		widgetA.removeColumn( 0 );
		assert.deepEqual( widgetA.model.data, widgetAexpectedDataAfterColumnRemoval, 'Columns are removed successfully' );

		widgetA.removeRow( -1 );
		assert.deepEqual( widgetA.model.data, widgetAexpectedDataAfterColumnRemoval, 'Invalid row removal by index does not change table data' );

		widgetA.removeRow( 'qux' );
		assert.deepEqual( widgetA.model.data, widgetAexpectedDataAfterColumnRemoval, 'Invalid row removal by key does not change table data' );

		assert.deepEqual( widgetB.getItems()[ 0 ].getItems()[ 2 ].getValue(), '13', 'Initial data is populated in text inputs properly' );
	} );
}() );
