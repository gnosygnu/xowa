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
import gplx.xowa.mediawiki.*; import gplx.xowa.mediawiki.includes.*; import gplx.xowa.mediawiki.includes.content.*;
class JCContentHandler extends TextContentHandler {	/**
	* Internal format to force pretty-printed json serialization
	*/
	private static final String CONTENT_FORMAT_JSON_PRETTY = "application/json+pretty";

	private JCSingleton singleton;
	/**
	* @param String $modelId
	*/
	public void __construct(String modelId, JCSingleton singleton) {
		super.__construct(modelId, XomwDefines.CONTENT_FORMAT_JSON, CONTENT_FORMAT_JSON_PRETTY);
		this.singleton = singleton;
	}

//		/**
//		* Returns the content's text as-is.
//		*
//		* @param \Content|JCContent $content This is actually a Content Object
//		* @param String|null $format
//		* @return mixed
//		*/
//		public function serializeContent(\Content $content, $format = null) {
//			this.checkFormat($format);
//			$status = $content->getStatus();
//			if ($status->isGood()) {
//				$data = $content->getData(); // There are no errors, normalize data
//			} elseif ($status->isOK()) {
//				$data = $content->getRawData(); // JSON is valid, but the data has errors
//			} else {
//				return $content->getNativeData(); // Invalid JSON - can't do anything with it
//			}
//
//			return FormatJson::encode($data, $format === self::CONTENT_FORMAT_JSON_PRETTY,
//				FormatJson::ALL_OK);
//		}
//
//		/**
//		* @param \Content|JCContent $oldContent
//		* @param \Content|JCContent $myContent
//		* @param \Content|JCContent $yourContent
//		* @return boolean|JCContent
//		*/
//		public function merge3(\Content $oldContent, \Content $myContent, \Content $yourContent) {
//			// Almost identical clone of the parent's merge3, except that we use pretty-printed merge,
//			// thus allowing much more lenient line-based merging.
//
//			this.checkModelID($oldContent->getModel());
//			this.checkModelID($myContent->getModel());
//			this.checkModelID($yourContent->getModel());
//
//			$format = self::CONTENT_FORMAT_JSON_PRETTY;
//
//			$old = this.serializeContent($oldContent, $format);
//			$mine = this.serializeContent($myContent, $format);
//			$yours = this.serializeContent($yourContent, $format);
//
//			$ok = wfMerge($old, $mine, $yours, $result);
//
//			if (!$ok) {
//				return false;
//			}
//
//			if (!$result) {
//				return this.makeEmptyContent();
//			}
//
//			$mergedContent = this.unserializeContent($result, $format);
//
//			return $mergedContent;
//		}
//
//		/**
//		* Returns the name of the diff engine to use.
//		*
//		* @since 1.21
//		*
//		* @return String
//		*/
//		protected function getDiffEngineClass() {
//			return JCJsonDifferenceEngine::class;
//		}
//
	/**
	* Unserializes a JsonSchemaContent Object.
	*
	* @param String $text Serialized form of the content
	* @param null|String $format The format used for serialization
	* @param boolean $isSaving Perform extra validation
	* @return JCContent the JsonSchemaContent Object wrapping $text
	*/
	public JCContent unserializeContent(byte[] text) {return unserializeContent(text, null, true);}
	public JCContent unserializeContent(byte[] text, String format, boolean isSaving) {
		this.checkFormat(format);
		String modelId = this.getModelID();
		XophpClassBldr factory = singleton.getContentClass(modelId);
		return (JCContent)factory.Make(text, modelId, isSaving);
	}

//		/**
//		* Returns the name of the associated Content class, to
//		* be used when creating new objects. Override expected
//		* by subclasses.
//		*
//		* @return String
//		*/
//		protected function getContentClass() {
//			$modelId = this.getModelID();
//			return JCSingleton::getContentClass($modelId);
//		}
//
//		/**
//		* Creates an empty JsonSchemaContent Object.
//		*
//		* @return JCContent
//		*/
//		public function makeEmptyContent() {
//			// Each model could have its own default JSON value
//			// null notifies that default should be used
//			return this.unserializeContent(null);
//		}
}
