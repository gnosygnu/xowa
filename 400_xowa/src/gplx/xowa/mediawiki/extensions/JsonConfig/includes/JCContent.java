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
import gplx.xowa.mediawiki.*; import gplx.xowa.mediawiki.includes.content.*;
public class JCContent extends TextContent { 	/** @var array */
	private Object rawData = null;
	/** @var stdClass|array */
	protected XophpStdClass data = null;
	/** @var Status */
	private XomwStatus status;
	/** @var boolean */
	private boolean thoroughVar;
	/** @var JCContentView|null contains an instance of the view class */
	// private Object view = null;

	/**
	* @param String text Json configuration. If null, default content will be inserted instead
	* @param String $modelId
	* @param boolean thorough True if extra validation should be performed
	*/
	public void __construct(byte[] text, String modelId, boolean thorough) {
		if (text == null) {
			// text = this.getView($modelId).getDefault($modelId);
		}
		super.__construct(text, modelId);
		this.thoroughVar = thorough;
		this.status = new XomwStatus();
		this.parse();
	}

	/**
	* Get validated data
	* @return stdClass|stdClass[]
	*/
	public XophpStdClass getData() {
		return this.data;
	}

	/**
	* Returns data after sanitization, suitable for third-party use
	*
	* @param stdClass|stdClass[] data
	* @return stdClass|stdClass[]
	*/
	public XophpStdClass getSafeData(XophpStdClass data) {
		return data;
	}

	/**
	* Returns JSON Object as resulted from parsing initial text,
	* before any validation/modifications took place
	* @return mixed
	*/
	public Object getRawData() {
		return this.rawData;
	}

	/**
	* Get content status Object
	* @return Status
	*/
	public XomwStatus getStatus() {
		return this.status;
	}

	/**
	* @return boolean False if this configuration has parsing or validation errors
	*/
	public boolean isValid() {
		return this.status.isGood();
	}

	private static final byte[] Bry__ary__empty = Bry_.new_a7("{}");
	public boolean isEmpty() {
		byte[] text = Bry_.Trim(this.getNativeData());
		return Bry_.Len_eq_0(text) || Bry_.Eq(text, Bry__ary__empty);
	}

	/**
	* Determines whether this content should be considered a "page" for statistics
	* In our case, just making sure it's not empty or a redirect
	* @param boolean $hasLinks
	* @return boolean
	*/
	public boolean isCountable(boolean hasLinks) {
		return !this.isEmpty() && !this.isRedirect();
	}

	/**
	* Returns true if the text is in JSON format.
	* @return boolean
	*/
	public boolean isValidJson() {
		return this.rawData != null;
	}

	/**
	* @return boolean true if thorough validation may be needed -
	*   e.g. rendering HTML or saving new value
	*/
	public boolean thorough() {
		return this.thoroughVar;
	}

	/**
	* Override this method to perform additional data validation
	* @param mixed data
	* @return mixed
	*/
	public XophpStdClass validate(XophpStdClass data) {
		return data;
	}

	/**
	* Perform initial json parsing and validation
	*/
	private void parse() {
//			String rawText = this.getNativeData();
//			parseOpts = FormatJson::STRIP_COMMENTS + FormatJson::TRY_FIXING;
//			status = FormatJson::parse(rawText, parseOpts);
//			if (!status.isOK()) {
//				this.status = status;
//				return;
//			}
//			data = status.getValue();
//			// @fixme: HACK - need a deep clone of the data
//			// @fixme: but doing (Object)(array)data will re-encode empty [] as {}
//			// @performance: re-encoding is likely faster than stripping comments in PHP twice
////			this.rawData = FormatJson::decode(
////				FormatJson::encode(data, FormatJson::ALL_OK), true
////			);
//			this.data = this.validate(data);
	}

//		/**
//		* Beautifies JSON prior to save.
//		* @param Title $title Title
//		* @param \User $user User
//		* @param \ParserOptions $popts
//		* @return JCContent
//		*/
//		public function preSaveTransform(Title $title, \User $user, \ParserOptions $popts) {
//			if (!this.isValidJson()) {
//				return this; // Invalid JSON - can't do anything with it
//			}
//			$formatted = FormatJson::encode(this.getData(), false, FormatJson::ALL_OK);
//			if (this.getNativeData() !== $formatted) {
//				return new static($formatted, this.getModel(), this.thorough());
//			}
//			return this;
//		}
//
//		protected function fillParserOutput(Title $title, $revId, ParserOptions $options,
//											$generateHtml, ParserOutput &$output) {
//			if (!$generateHtml) {
//				return;
//			}
//
//			status = this.getStatus();
//			if (!status.isGood()) {
//				// Use user's language, and split parser cache.  This should not have a big
//				// impact because data namespace is rarely viewed, but viewing it localized
//				// will be valuable
//				$lang = $options.getUserLangObj();
//				$html = status.getHTML(false, false, $lang);
//			} else {
//				$html = '';
//			}
//
//			if (status.isOK()) {
//				$html .= this
//					.getView(this.getModel())
//					.valueToHtml(this, $title, $revId, $options, $generateHtml, $output);
//			}
//
//			$output.setText($html);
//		}
//
//		/**
//		* Get a view Object for this content Object
//		* @param String $modelId is required here because parent ctor might not have ran yet
//		* @return JCContentView
//		*/
//		protected function getView($modelId) {
//			global $wgJsonConfigModels;
//			view = this.view;
//			if (view === null) {
//				$configModels = \ExtensionRegistry::getInstance().getAttribute('JsonConfigModels')
//					+ $wgJsonConfigModels;
//				if (array_key_exists($modelId, $configModels)) {
//					$value = $configModels[$modelId];
//					if (is_array($value) && array_key_exists('view', $value)) {
//						$class = $value['view'];
//						view = new $class();
//					}
//				}
//				if (view === null) {
//					view = this.createDefaultView();
//				}
//				this.view = view;
//			}
//			return view;
//		}
//
//		/**
//		* In case view is not associated with the model for this class, this function will instantiate
//		* a default. Override may instantiate a more appropriate view
//		* @return JCContentView
//		*/
//		protected function createDefaultView() {
//			return new JCDefaultContentView();
//		}
}
