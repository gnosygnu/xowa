/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2017 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.xowa.mediawiki.extensions.JsonConfig.includes; import gplx.*;
import gplx.objects.arrays.ArrayUtl;
import gplx.objects.arrays.ArrayUtl;
import gplx.xowa.mediawiki.*;
public class JCObjContent extends JCContent { //		/**
//		* @var boolean if false, prevents multiple fields from having identical names that differ
//		*   only by casing
//		*/
//		protected $isCaseSensitive = false;
//
//		/** @var boolean if false, ensure the root to be an stdClass, otherwise - an array */
//		protected $isRootArray = false;
//
//		/**
//		* @var JCValue contains raw validation results. At first it is a parsed JSON value, with the
//		*   root element wrapped into JCValue. As validation progresses, all visited values become
//		*   wrapped with JCValue.
//		*/
//		protected $validationData;
//
//		/** @var mixed  */
//		protected $dataWithDefaults;
//
//		/** @var boolean|null validation status - null=before, true=during, false=done */
//		protected $isValidating = null;
//
//		/**
//		* Override default behavior to include defaults if validation succeeded.
//		*
//		* @return String|boolean The raw text, or false if the conversion failed.
//		*/
//		public function getWikitextForTransclusion() {
//			if ( !$this->getStatus()->isGood() ) {
//				// If validation failed, return original text
//				return parent::getWikitextForTransclusion();
//			}
//			if ( !$this->thorough() && $this->validationData !== null ) {
//				// ensure that data is sorted in the right order
//				self::markUnchecked( $this->validationData );
//			}
//			return \FormatJson::encode( $this->getDataWithDefaults(), true, \FormatJson::ALL_OK );
//		}
//
//		protected function createDefaultView() {
//			return new JCDefaultObjContentView();
//		}
//
//		/**
//		* Get configuration data with custom defaults
//		* @throws \Exception in case validation is not complete
//		* @return mixed
//		*/
//		public function getDataWithDefaults() {
//			if ( $this->isValidating !== false ) {
//				throw new Exception( 'This method may only be called after validation is complete' );
//			}
//			if ( $this->dataWithDefaults === null ) {
//				$this->dataWithDefaults = JCUtils::sanitize( $this->validationData );
//			}
//			return $this->dataWithDefaults;
//		}
//
//		/**
//		* Get status array that recursively describes dataWithDefaults
//		* @throws \Exception
//		* @return JCValue
//		*/
//		public function getValidationData() {
//			if ( $this->isValidating === null ) {
//				throw new Exception(
//					'This method may only be called during or after validation has started'
//				);
//			}
//			return $this->validationData;
//		}
//
//		/**
//		* Call this function before performing data validation inside the derived validate()
//		* @param array|Object $data
//		* @throws \Exception
//		* @return boolean if true, validation should be performed, otherwise all checks will be ignored
//		*/
//		protected function initValidation( $data ) {
//			if ( $this->isValidating !== null ) {
//				throw new Exception( 'This method may only be called before validation has started' );
//			}
//			$this->isValidating = true;
//			if ( !$this->isRootArray && !is_object( $data ) ) {
//				$this->getStatus()->fatal( 'jsonconfig-err-root-Object-expected' );
//			} elseif ( $this->isRootArray && !is_array( $data ) ) {
//				$this->getStatus()->fatal( 'jsonconfig-err-root-array-expected' );
//			} else {
//				$this->validationData = new JCValue( JCValue::UNCHECKED, $data );
//				return true;
//			}
//			return false;
//		}
//
//		/**
//		* Derived validate() must return the result of this function
//		* @throws \Exception
//		* @return array
//		*/
//		protected function finishValidation() {
//			if ( !$this->getStatus()->isGood() ) {
//				return $this->getRawData(); // validation failed, do not modify
//			}
//			return null; // Data will be filter-cloned on demand inside self::getData()
//		}
//
//		/**
//		* Populate this data on-demand for efficiency
//		* @return array
//		*/
//		public function getData() {
//			if ( $this->data === null ) {
//				$this->data = JCUtils::sanitize( $this->validationData, true );
//			}
//			return $this->data;
//		}
//
//		public function validate( $data ) {
//			if ( $this->initValidation( $data ) ) {
//				$this->validateContent();
//				$data = $this->finishValidation();
//			}
//			if ( $this->thorough() && $this->validationData !== null ) {
//				self::markUnchecked( $this->validationData );
//			}
//			$this->isValidating = false;
//			return $data;
//		}
//
//		/**
//		* Derived classes must implement this method to perform custom validation
//		* using the test(...) calls
//		*/
//		abstract public function validateContent();
//
//		/**
//		* Use this function to test a value, or if the value is missing, use the default value.
//		* The value will be tested with validator(s) if provided, even if it was the default.
//		* @param String|array $path name of the root field to check, or a path to the field in a nested
//		*        structure. Nested path should be in the form of
//		*        [ 'field-level1', 'field-level2', ... ]. For example, if client needs to check
//		*        validity of the 'value1' in the structure {'key':{'sub-key':['value0','value1']}},
//		*        $field should be set to [ 'key', 'sub-key', 1 ].
//		* @param mixed $default value to be used in case field is not found. $default is passed to the
//		*        validator if validation fails. If validation of the default passes,
//		*        the value is considered optional.
//		* @param callable $validator callback function as defined in JCValidators::run(). More than one
//		*        validator may be given. If validators are not provided, any value is accepted
//		* @return boolean true if ok, false otherwise
//		* @throws \Exception if $this->initValidation() was not called.
//		*/
//		public function testOptional( $path, $default, $validator = null ) {
//			$vld = self::convertValidators( $validator, func_get_args(), 2 );
//			// first validator will replace missing with the default
//			array_unshift( $vld, JCValidators::useDefault( $default ) );
//			return $this->testInt( $path, $vld );
//		}
//
//		/**
//		* Use this function to test a field in the data. If missing, the validator(s) will receive
//		* JCMissing singleton as a value, and it will be up to the validator(s) to accept it or not.
//		* @param String|array $path name of the root field to check, or a path to the field in a nested
//		*        structure. Nested path should be in the form of
//		*        [ 'field-level1', 'field-level2', ... ]. For example, if client needs to check
//		*        validity of the 'value1' in the structure {'key':{'sub-key':['value0','value1']}},
//		*        $field should be set to [ 'key', 'sub-key', 1 ].
//		* @param callable $validator callback function as defined in JCValidators::run().
//		*        More than one validator may be given.
//		*        If validators are not provided, any value is accepted
//		* @throws \Exception
//		* @return boolean true if ok, false otherwise
//		*/
//		public function test( $path, $validator /*...*/ ) {
//			$vld = self::convertValidators( $validator, func_get_args(), 1 );
//			return $this->testInt( $path, $vld );
//		}
//
//		/**
//		* Use this function to test all values inside an array or an Object at a given path.
//		* All validators will be called for each of the sub-values. If there is no value
//		* at the given $path, or it is not a container, no action will be taken and no errors reported
//		* @param String|array $path path to the container field in a nested structure.
//		*        Nested path should be in the form of [ 'field-level1', 'field-level2', ... ].
//		*        For example, if client needs to check validity of the 'value1' in the structure
//		*        {'key':{'sub-key':['value0','value1']}},
//		*        $field should be set to [ 'key', 'sub-key', 1 ].
//		* @param callable $validator callback function as defined in JCValidators::run().
//		*        More than one validator may be given.
//		*        If validators are not provided, any value is accepted
//		* @throws \Exception
//		* @return boolean true if all values tested ok, false otherwise
//		*/
//		public function testEach( $path, $validator = null /*...*/ ) {
//			$vld = self::convertValidators( $validator, func_get_args(), 1 );
//			$isOk = true;
//			$path = (array)$path;
//			$containerField = $this->getField( $path );
//			if ( $containerField ) {
//				$container = $containerField->getValue();
//				if ( is_array( $container ) || is_object( $container ) ) {
//					$lastIdx = count( $path );
//					if ( is_object( $container ) ) {
//						$container = get_object_vars( $container );
//					}
//					foreach ( array_keys( $container ) as $k ) {
//						$path[$lastIdx] = $k;
//						$isOk &= $this->testInt( $path, $vld );
//					}
//				}
//			}
//			return $isOk;
//		}
//
//		/**
//		* @param array|String $path
//		* @param array $validators
//		* @return boolean
//		* @throws \Exception
//		*/
//		private function testInt( $path, $validators ) {
//			if ( !$this->getStatus()->isOK() ) {
//				return false; // skip all validation in case of a fatal error
//			}
//			if ( $this->isValidating !== true ) {
//				throw new Exception(
//					'This function should only be called inside the validateContent() override'
//				);
//			}
//			return $this->testRecursive( (array)$path, [], $this->validationData, $validators );
//		}
//
//		/**
//		* @param array $path
//		* @param array $fldPath For error reporting, path to the current field
//		* @param JCValue $jcv
//		* @param mixed $validators
//		* @throws \Exception
//		* @@gplx.Internal protected param JCValue $status
//		* @return boolean
//		*/
//		private function testRecursive( array $path, array $fldPath, JCValue $jcv, $validators ) {
//			// Go recursively through all fields in path until empty, and validate last
//			if ( !$path ) {
//				// keep this branch here since we allow validation of the whole Object ($path==[])
//				return $this->testValue( $fldPath, $jcv, $validators );
//			}
//			$fld = array_shift( $path );
//			if ( is_array( $jcv->getValue() ) && ctype_digit( $fld ) ) {
//				$fld = (int)$fld;
//			}
//			if ( !is_int( $fld ) && !is_string( $fld ) ) {
//				throw new Exception( 'Unexpected field type, only strings and integers are allowed' );
//			}
//			$fldPath[] = $fld;
//
//			$subJcv = $this->getField( $fld, $jcv );
//			if ( $subJcv === null ) {
//				$msg =
//					is_int( $fld ) && !is_array( $jcv->getValue() ) ? 'jsonconfig-err-array-expected'
//						: 'jsonconfig-err-Object-expected';
//				$this->addValidationError( wfMessage( $msg, JCUtils::fieldPathToString( $fldPath ) ) );
//				return false;
//			}
//
//			/** @var boolean $reposition - should the field be deleted and re-added at the end
//			* this is only needed for viewing and saving */
//			$reposition = $this->thorough() && is_string( $fld ) && $subJcv !== false;
//			if ( $subJcv === false || $subJcv->isUnchecked() ) {
//				// We never went down this path before
//				// Check that field exists, and is not case-duplicated
//				if ( is_int( $fld ) ) {
//					if ( count( $jcv->getValue() ) < $fld ) {
//						// Allow existing index or index+1 for appending last item
//						throw new Exception( "List index is too large at '" .
//											JCUtils::fieldPathToString( $fldPath ) .
//											"'. Index may not exceed list size." );
//					}
//				} elseif ( !$this->isCaseSensitive ) {
//					// if we didn't find it before, it could have been misnamed
//					$norm = $this->normalizeField( $jcv, $fld, $fldPath );
//					if ( $norm === null ) {
//						return false;
//					} elseif ( $norm ) {
//						$subJcv = $this->getField( $fld, $jcv );
//						$reposition = false; // normalization already does that
//					}
//				}
//				if ( $subJcv === null ) {
//					throw new Exception( 'Logic error - subJcv must be valid here' );
//				} elseif ( $subJcv === false ) {
//					// field does not exist
//					$initValue = !$path ? null : ( is_string( $path[0] ) ? new stdClass() : [] );
//					$subJcv = new JCValue( JCValue::MISSING, $initValue );
//				}
//			}
//			$isOk = $this->testRecursive( $path, $fldPath, $subJcv, $validators );
//
//			// Always remove and re-append the field
//			if ( $subJcv->isMissing() ) {
//				$jcv->deleteField( $fld );
//			} else {
//				if ( $reposition ) {
//					$jcv->deleteField( $fld );
//				}
//				$jcv->setField( $fld, $subJcv );
//				if ( $jcv->isMissing() || $jcv->isUnchecked() ) {
//					$jcv->status( JCValue::VISITED );
//				}
//			}
//			return $isOk;
//		}
//
//		/**
//		* @param array $fldPath
//		* @param JCValue $jcv
//		* @param array $validators
//		* @return boolean
//		*/
//		private function testValue( array $fldPath, JCValue $jcv, $validators ) {
//			// We have reached the last level of the path, test the actual value
//			if ( $validators !== null ) {
//				$isRequired = $jcv->defaultUsed();
//				JCValidators::run( $validators, $jcv, $fldPath, $this );
//				$err = $jcv->error();
//				if ( $err ) {
//					if ( is_object( $err ) ) {
//						// if ( !$isRequired ) {
//						// // User supplied value, so we don't know if the value is required or not
//						// // if $default passes validation, original value was optional
//						// $isRequired = !JCValidators::run(
//						// $validators, $fldPath, JCValue::getMissing(), $this
//						// );
//						// }
//						$this->addValidationError( $err, !$isRequired );
//					}
//					return false;
//				} elseif ( $jcv->isUnchecked() ) {
//					$jcv->status( JCValue::CHECKED );
//				}
//			}
//			// if ( $this->thorough() && $jcv->status() === JCValue::CHECKED ) {
//			// // Check if the value is the same as default - use a cast to array
//			// // hack to compare objects
//			// $isRequired = (boolean)JCValidators::run( $validators, $fldPath, JCMissing::get(), $this );
//			// if ( ( is_object( $jcv ) && is_object( $default ) && (array)$jcv === (array)$default )
//			// || ( !is_object( $default ) && $jcv === $default )
//			// ) {
//			// $newStatus = JCValue::SAME_AS_DEFAULT;
//			// }
//			// }
//			return true;
//		}
//
//		/**
//		* Recursively reorder all sub-elements - checked first, followed by unchecked.
//		* Also, convert all sub-elements to JCValue(UNCHECKED) if at least one of them was JCValue
//		* This is useful for HTML rendering to indicate unchecked items
//		* @param JCValue $data
//		*/
//		private static function markUnchecked( JCValue $data ) {
//			$val = $data->getValue();
//			$isObject = is_object( $val );
//			if ( !$isObject && !is_array( $val ) ) {
//				return;
//			}
//			$result = null;
//			$firstPass = true;
//			$hasJcv = false;
//			// Two pass loop - first pass moves all checked values to the result,
//			// second pass moves the rest of of the values, possibly converting them to JCValue
//			while ( true ) {
//				foreach ( $val as $key => $subVal ) {
//					/** @var JCValue|mixed $subVal */
//					$isJcv = is_a( $subVal, '\JsonConfig\JCValue' );
//					if ( $firstPass && $isJcv ) {
//						// On the first pass, recursively process subelements if they were visited
//						self::markUnchecked( $subVal );
//						$move = $isObject && !$subVal->isUnchecked();
//						$hasJcv = true;
//					} else {
//						$move = false;
//					}
//					if ( $move || !$firstPass ) {
//						if ( !$isJcv ) {
//							$subVal = new JCValue( JCValue::UNCHECKED, $subVal );
//						}
//						if ( $result === null ) {
//							$result = $isObject ? new stdClass() : [];
//						}
//						if ( $isObject ) {
//							$result->$key = $subVal;
//							unset( $val->$key );
//						} else {
//							// No need to unset - all values in an array are moved in the second pass
//							$result[] = $subVal;
//						}
//					}
//				}
//
//				if ( ( $result === null && !$hasJcv ) || !$firstPass ) {
//					// either nothing was found, or we are done with the second pass
//					if ( $result !== null ) {
//						$data->setValue( $result );
//					}
//					return;
//				}
//				$firstPass = false;
//			}
//		}
//
//		/**
//		* @param Message $error
//		* @param boolean $isOptional
//		*/
//		public function addValidationError( Message $error, $isOptional = false ) {
//			$text = $error->plain();
//			// @TODO fixme - need to re-enable optional field detection & reporting
//			// if ( $isOptional ) {
//			// $text .= ' ' . wfMessage( 'jsonconfig-optional-field' )->plain();
//			// }
//			$this->getStatus()->error( $text );
//		}

	/** Get field from data Object/array
	* @param String|int|array $field
	* @param stdClass|array|JCValue $data
	* @throws \Exception
	* @return false|null|JCValue search result:
	*      false if not found
	*      null if error (argument type does not match storage)
	*      JCValue if the value is found
	*/
	public JCValue getField(int      field)               {return getFieldWkr(field, null, null, null);}
	public JCValue getField(int      field, Object data)  {return getFieldWkr(field, null, null, data);}
	public JCValue getField(String   field)               {return getFieldWkr(-1, field, null, null);}
	public JCValue getField(String   field, Object data)  {return getFieldWkr(-1, field, null, data);}
	public JCValue getField(String[] fields)              {return getFieldWkr(-1, null, fields, null);}
	public JCValue getField(String[] fields, Object data) {return getFieldWkr(-1, null, fields, data);}
	public JCValue getFieldWkr(int fldInt, String fldStr, String[] fldAry, Object data) {
		if (data == null) {
//				data = this.getValidationData();
		}
		if (fldAry == null) {
			data = getFieldByItem(fldInt, fldStr, data);
			if (data == null)
				return null;
		}
		else {
			for (String fld : fldAry) {
				data = getFieldByItem(-1, fld, data);
				if (data == null)
					return null;
			}
		}
		if (Type_.Eq_by_obj(data, JCValue.class)) {
			return (JCValue)data;
		} else {
//				return new JCValue(JCValue.UNCHECKED, data);
			return null;
		}
	}
	private Object getFieldByItem(int fldInt, String fldStr, Object data) {
		if (fldInt == -1 && fldStr == null) {
			throw Err_.new_wo_type("Field must be either int or String");
		}
		
		if (Type_.Eq_by_obj(data, JCValue.class)) {
			data = ((JCValue)data).getValue();
		}
		int typeId = XomwTypeUtl.To_type_id(data);
		boolean isObject = typeId == Type_ids_.Id__obj;
		boolean isArray = typeId == Type_ids_.Id__array;
		if (fldStr != null ? !(isObject || isArray) : !isArray) {
			return null;
		}

		if (isObject) {
			XophpStdClass dataAsMap = (XophpStdClass)data;
			return dataAsMap.Get_by_as_itm(fldStr);
		} else if (isArray) {
			Object dataAsAry = ArrayUtl.Cast(data);
			if (fldInt < ArrayUtl.Len(dataAsAry))
				return ArrayUtl.GetAt(dataAsAry, fldInt);
		}
		return null;
	}

//		/**
//		* @param JCValue $jcv
//		* @param int|String $fld
//		* @param array $fldPath
//		* @throws \Exception
//		* @return boolean|null true if renamed, false if not found or original unchanged,
//		*   null if duplicate (error)
//		*/
//		private function normalizeField( JCValue $jcv, $fld, array $fldPath ) {
//			$valueRef = $jcv->getValue();
//			$foundFld = false;
//			$isError = false;
//			foreach ( $valueRef as $k => $v ) {
//				if ( 0 === strcasecmp( $k, $fld ) ) {
//					if ( $foundFld !== false ) {
//						$isError = true;
//						break;
//					}
//					$foundFld = $k;
//				}
//			}
//			if ( $isError ) {
//				$this->addValidationError( wfMessage( 'jsonconfig-duplicate-field',
//						JCUtils::fieldPathToString( $fldPath ) ) );
//				if ( $this->thorough() ) {
//					// Mark all duplicate fields as errors
//					foreach ( $valueRef as $k => $v ) {
//						if ( 0 === strcasecmp( $k, $fld ) ) {
//							if ( !is_a( $v, '\JsonConfig\JCValue' ) ) {
//								$v = new JCValue( JCValue::UNCHECKED, $v );
//								$jcv->setField( $k, $v );
//							}
//							$v->error( true );
//						}
//					}
//				}
//				return null;
//			} elseif ( $foundFld !== false && $foundFld !== $fld ) {
//				// key had different casing, rename it to canonical
//				$jcv->setField( $fld, $jcv->deleteField( $foundFld ) );
//				return true;
//			}
//			return false;
//		}
//
//		/**
//		* @param null|callable|array $param first validator parameter
//		* @param array $funcArgs result of func_get_args() call
//		* @param int $skipArgs how many non-validator arguments to remove
//		*   from the beginning of the $funcArgs
//		* @return array of validators
//		*/
//		private static function convertValidators( $param, $funcArgs, $skipArgs ) {
//			if ( $param === null ) {
//				return []; // no validators given
//			} elseif ( is_array( $param ) && !is_callable( $param, true ) ) {
//				return $param; // first argument is an array of validators
//			} else {
//				return array_slice( $funcArgs, $skipArgs ); // remove fixed params from the beginning
//			}
//		}
}
