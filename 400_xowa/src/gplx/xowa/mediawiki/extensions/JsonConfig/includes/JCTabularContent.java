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
package gplx.xowa.mediawiki.extensions.JsonConfig.includes; import gplx.*; import gplx.xowa.*; import gplx.xowa.mediawiki.*; import gplx.xowa.mediawiki.extensions.*; import gplx.xowa.mediawiki.extensions.JsonConfig.*;
import gplx.core.primitives.*;
import gplx.xowa.mediawiki.*;
import gplx.xowa.langs.*;
public class JCTabularContent extends JCDataContent {//		protected function createDefaultView() {
//			return new JCTabularContentView();
//		}

//		/**
//		* Returns wiki-table representation of the tabular data
//		*
//		* @return String|boolean The raw text, or false if the conversion failed.
//		*/
//		public function getWikitextForTransclusion() {
//			$toWiki = function ( $value ) {
//				if ( is_object( $value ) ) {
//					global $wgLang;
//					$value = JCUtils::pickLocalizedString( $value, $wgLang );
//				}
//				if ( preg_match( '/^[ .\pL\pN]*$/i', $value ) ) {
//					// Optimization: spaces, letters, numbers, and dots are returned without <nowiki>
//					return $value;
//				}
//				return '<nowiki>' . htmlspecialchars( $value ) . '</nowiki>';
//			};
//
//			$data = $this->getData();
//			$result = "{| class='wikitable sortable'\n";
//
//			// Create header
//			$result .= '!' . implode( "!!",
//				array_map(
//					function ( $field ) use ( $toWiki ) {
//						return $toWiki( $field->title ? : $field->name );
//					},
//					$data->schema->fields
//				)
//			) . "\n";
//
//			// Create table content
//			foreach ( $data->data as $row ) {
//				$result .= "|-\n|" . implode( '||', array_map( $toWiki, $row ) ) . "\n";
//			}
//
//			$result .= "\n|}\n";
//
//			return $result;
//		}

	/**
	* Derived classes must implement this method to perform custom validation
	* using the check(...) calls
	*/
	public void validateContent() {
//			parent::validateContent();
//
//			$validators = [ JCValidators::isList() ];
//			$typeValidators = [];
//			$fieldsPath = [ 'schema', 'fields' ];
//			if ( $this->test( 'schema', JCValidators::isDictionary() ) &&
//				$this->test( $fieldsPath, JCValidators::isList() ) &&
//				$this->testEach( $fieldsPath, JCValidators::isDictionary() )
//			) {
//				$hasError = false;
//				$allHeaders = [];
//				$fieldCount = count( $this->getField( $fieldsPath )->getValue() );
//				for ( $idx = 0; $idx < $fieldCount; $idx++ ) {
//					$header = false;
//					$hasError |= !$this->test( [ 'schema', 'fields', $idx, 'name' ],
//						JCValidators::isHeaderString( $allHeaders ),
//						function ( JCValue $jcv ) use ( &$header ) {
//							$header = $jcv->getValue();
//							return true;
//						} );
//					$hasError |= !$this->test( [ 'schema', 'fields', $idx, 'type' ],
//						JCValidators::validateDataType( $typeValidators ) );
//					if ( $header ) {
//						$hasError |= !$this->testOptional( [ 'schema', 'fields', $idx, 'title' ],
//							function () use ( $header ) {
//								return (Object)[ 'en' => $header ];
//							}, JCValidators::isLocalizedString() );
//					}
//				}
//				$countValidator = JCValidators::checkListSize( $fieldCount, 'schema/fields' );
//				$validators[] = $countValidator;
//
//				if ( !$hasError ) {
//					$this->testEach( $fieldsPath, JCValidators::noExtraValues() );
//				}
//			}
//			$this->test( 'schema', JCValidators::noExtraValues() );
//
//			if ( !$this->thorough() ) {
//				// We are not doing any modifications to the data, so no need to validate it
//				return;
//			}
//
//			$this->test( 'data', JCValidators::isList() );
//			$this->test( [], JCValidators::noExtraValues() );
//			$this->testEach( 'data', $validators );
//			if ( $typeValidators ) {
//				/** @noinspection PhpUnusedParameterInspection */
//				$this->testEach( 'data', function ( JCValue $v, array $path ) use ( $typeValidators ) {
//					$isOk = true;
//					$lastIdx = count( $path );
//					foreach ( array_keys( $typeValidators ) as $k ) {
//						$path[$lastIdx] = $k;
//						$isOk &= $this->test( $path, $typeValidators[$k] );
//					}
//					return $isOk;
//				} );
//			}
	}

	/**
	* Resolve @Override any specific localizations, and add it to $result
	* @param Object $result
	* @param Language $lang
	*/
	@Override protected void localizeData(XophpStdClass result, Xol_lang_itm lang) {
		super.localizeData(result, lang);

		XophpStdClass data = this.getData();
		JCLocalizeItmFunc localize = new JCLocalizeItmFunc(lang);

		Int_list isLocalized = new Int_list();
		result.Set_by_as_itm("schema", new XophpStdClass());

		XophpStdClass result_schema_flds = new XophpStdClass();
		result.Set_by_as_itm(String_.Ary("schema", "fields"), result_schema_flds);
		XophpStdClass flds = data.Get_by_ary_as_itm("schema", "fields");
		int flds_len = flds.Len();
		for (int ind = 0; ind < flds_len; ind++) {
			XophpStdClass fld = flds.Get_at_as_itm(ind);
			if (fld.Comp_str("type", "localized")) {
				isLocalized.Add(ind);
			}
			XophpStdClass rslt_fld = new XophpStdClass();
			rslt_fld.Set_by_as_str("name", fld.Get_by_as_str("name"));
			rslt_fld.Set_by_as_str("type", fld.Get_by_as_str("type"));
			rslt_fld.Set_by_as_str("title", fld.Has("title") ? localize.Localize(fld.Get_by_as_itm("title")) : fld.Get_by_as_str("name"));
			result_schema_flds.Add_at_as_itm(rslt_fld);
		}

		if (isLocalized.Len() == 0) {
			// There are no localized strings in the data, optimize
			result.Set_by_as_itm("data", data.Get_by_as_itm("data"));
		}
		else {
			JCArrayFunc array_map_func = new JCArrayFunc();
			result.Set_by_as_itm("data", array_map_func.Array_map(new JCLocalizeAryFunc(localize, isLocalized), data.Get_by_as_itm("data")));
		}
	}
	public static final    String Model_id = "JCTabularContent";
}
class JCArrayFunc {
	public XophpStdClass Array_map(JCLocalizeAryFunc func, XophpStdClass src) {
		XophpStdClass trg = new XophpStdClass();
		int len = src.Len();
		for (int i = 0; i < len; i++) {
			XophpStdClass src_sub = src.Get_at_as_itm(i);
			XophpStdClass trg_sub = func.Array_map(src_sub);
			trg.Add_at_as_itm(trg_sub);
		}
		return trg;
	}
}
class JCLocalizeAryFunc {
	private final    JCLocalizeItmFunc localize;
	private final    Int_list isLocalized;
	public JCLocalizeAryFunc(JCLocalizeItmFunc localize, Int_list isLocalized) {
		this.localize = localize;
		this.isLocalized = isLocalized;
	}
	public XophpStdClass Array_map(XophpStdClass row) {
		int len = isLocalized.Len();
		for (int ind = 0; ind < len; ind++) {
			XophpStdClass val = row.Get_at_as_itm(ind);
			if (val != null) {
				row.Set_at_as_str(ind, localize.Localize(val)); // NOTE: will reduce a map to a String; EX: name={en='a',fr='b'} => name={'a'}
			}
		}
		return row;
	}
}
class JCLocalizeItmFunc {
	private final    Xol_lang_itm lang;
	public JCLocalizeItmFunc(Xol_lang_itm lang) {
		this.lang = lang;
	}
	public String Localize(XophpStdClass val) {
		return JCUtils.pickLocalizedString(val, lang);
	}
}
