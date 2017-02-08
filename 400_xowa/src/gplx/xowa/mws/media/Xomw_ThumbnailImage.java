/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012 gnosygnu@gmail.com

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package gplx.xowa.mws.media; import gplx.*; import gplx.xowa.*; import gplx.xowa.mws.*;
import gplx.langs.htmls.*;
import gplx.xowa.mws.utls.*;
import gplx.xowa.mws.parsers.lnkis.*;
import gplx.xowa.mws.filerepo.file.*;
// Media transform output for images
public class Xomw_ThumbnailImage extends Xomw_MediaTransformOutput {	private final    List_adp attribs = List_adp_.New(), link_attribs = List_adp_.New();
	public Xomw_ThumbnailImage(Xomw_File file, byte[] url, byte[] path, int w, int h) {super(file, url, path, w, h);
	}
	/**
	* Get a thumbnail Object from a file and parameters.
	* If path is set to null, the output file is treated as a source copy.
	* If path is set to false, no output file will be created.
	* parameters should include, as a minimum, (file) 'width' and 'height'.
	* It may also include a 'page' parameter for multipage files.
	*
	* @param File file
	* @param String url URL path to the thumb
	* @param String|boolean path Filesystem path to the thumb
	* @param array parameters Associative array of parameters
	*/
	public Xomw_ThumbnailImage(Xomw_File file, byte[] url, byte[] path, Xomw_params_handler parameters) {super(file, url, path, parameters.width, parameters.height);
//			defaults = [
//				'page' => false,
//				'lang' => false
//			];
//
//			if (is_array(parameters)) {
//				actualParams = parameters + defaults;
//			} else {
//				// Using old format, should convert. Later a warning could be added here.
//				numArgs = func_num_args();
//				actualParams = [
//					'width' => path,
//					'height' => parameters,
//					'page' => (numArgs > 5) ? func_get_arg(5) : false
//				] + defaults;
//				path = (numArgs > 4) ? func_get_arg(4) : false;
//			}

//			this->file = file;
//			this->url = url;
//			this->path = path;

		// These should be integers when they get here.
		// If not, there's a bug somewhere.  But let's at
		// least produce valid HTML code regardless.
//			this->width = round(actualParams['width']);
//			this->height = round(actualParams['height']);

//			this->page = actualParams['page'];
//			this->lang = actualParams['lang'];
	}

	/**
	* Return HTML <img ... /> tag for the thumbnail, will include
	* width and height attributes and a blank alt text (as required).
	*
	* @param array options Associative array of options. Boolean options
	*     should be indicated with a value of true for true, and false or
	*     absent for false.
	*
	*     alt          HTML alt attribute
	*     title        HTML title attribute
	*     desc-link    Boolean, show a description link
	*     file-link    Boolean, show a file download link
	*     valign       vertical-align property, if the output is an inline element
	*     img-class    Class applied to the \<img\> tag, if there is such a tag
	*     desc-query   String, description link query prms
	*    @Override  width     Override width attribute. Should generally not set
	*    @Override  height    Override height attribute. Should generally not set
	*     no-dimensions      Boolean, skip width and height attributes (useful if
	*                        set in CSS)
	*     custom-url-link    Custom URL to link to
	*     custom-title-link  Custom Title Object to link to
	*     custom target-link Value of the target attribute, for custom-target-link
	*     parser-extlink-*   Attributes added by parser for external links:
	*          parser-extlink-rel: add rel="nofollow"
	*          parser-extlink-target: link target, but overridden by custom-target-link
	*
	* For images, desc-link and file-link are implemented as a click-through. For
	* sounds and videos, they may be displayed in other ways.
	*
	* @throws MWException
	* @return String
	*/
	// Return HTML <img ... /> tag for the thumbnail, will include
	// width and height attributes and a blank alt text (as required).
	//
	// @param array options Associative array of options. Boolean options
	//     should be indicated with a value of true for true, and false or
	//     absent for false.
	//
	//     alt          HTML alt attribute
	//     title        HTML title attribute
	//     desc-link    Boolean, show a description link
	//     file-link    Boolean, show a file download link
	//     valign       vertical-align property, if the output is an inline element
	//     img-class    Class applied to the \<img\> tag, if there is such a tag
	//     desc-query   String, description link query prms
	//     override-width     Override width attribute. Should generally not set
	//     override-height    Override height attribute. Should generally not set
	//     no-dimensions      Boolean, skip width and height attributes (useful if
	//                        set in CSS)
	//     custom-url-link    Custom URL to link to
	//     custom-title-link  Custom Title Object to link to
	//     custom target-link Value of the target attribute, for custom-target-link
	//     parser-extlink-*   Attributes added by parser for external links:
	//          parser-extlink-rel: add rel="nofollow"
	//          parser-extlink-target: link target, but overridden by custom-target-link
	//
	// For images, desc-link and file-link are implemented as a click-through. For
	// sounds and videos, they may be displayed in other ways.
	// XO.MW:SYNC:1.29; DATE:2017-02-03
	@Override public void toHtml(Bry_bfr bfr, Bry_bfr tmp, Xomw_params_mto options) {
		byte[] alt = options.alt;

//			byte[] query = options.desc_query;

		attribs.Clear();
		attribs.Add_many(Gfh_atr_.Bry__alt, alt);
		attribs.Add_many(Gfh_atr_.Bry__src, url);
		boolean link_attribs_is_null = false;
		if (!Php_utl_.Empty(options.custom_url_link)) {
			link_attribs.Clear();
			link_attribs.Add_many(Gfh_atr_.Bry__href, options.custom_url_link);
			if (!Php_utl_.Empty(options.title)) {
				link_attribs.Add_many(Gfh_atr_.Bry__title, options.title);
			}
			if (Php_utl_.Empty(options.custom_target_link)) {
				link_attribs.Add_many(Gfh_atr_.Bry__target, options.custom_target_link);
			}
			else if (Php_utl_.Empty(options.parser_extlink_target)) {
				link_attribs.Add_many(Gfh_atr_.Bry__target, options.parser_extlink_target);
			}
			if (Php_utl_.Empty(options.parser_extlink_rel)) {
				link_attribs.Add_many(Gfh_atr_.Bry__rel, options.parser_extlink_rel);
			}
		}
		else if (!Php_utl_.Empty(options.custom_title_link)) {
//				byte[] title = options.custom_title_link;
//				link_attribs.Clear();
//				link_attribs.Add_many(Gfh_atr_.Bry__href, title.Get_link_url());
//				byte[] options_title = options.title;
//				link_attribs.Add_many(Gfh_atr_.Bry__title, Php_utl_.Empty(options_title) ? title.Get_full_text() : options_title);
		}
		else if (!Php_utl_.Empty(options.desc_link)) {
//				link_attribs = this.getDescLinkAttribs(
//					empty(options['title']) ? null : options['title'],
//					$query
//				);
		}
		else if (!Php_utl_.Empty(options.file_link)) {
//				link_attribs.Clear();
//				link_attribs.Add_many(Gfh_atr_.Bry__href, file.Get_url());
		}
		else {
			link_attribs_is_null = true;
			if (!Php_utl_.Empty(options.title)) {
				attribs.Add_many(Gfh_atr_.Bry__title, options.title);
			}
		}

		if (!Php_utl_.Empty(options.no_dimensions)) {
			attribs.Add_many(Gfh_atr_.Bry__width, Int_.To_bry(width));
			attribs.Add_many(Gfh_atr_.Bry__height, Int_.To_bry(height));
		}
		if (!Php_utl_.Empty(options.valign)) {
			attribs.Add_many(Gfh_atr_.Bry__style, Bry_.Add(Bry__vertical_align, options.valign));
		}
		if (!Php_utl_.Empty(options.img_cls)) {
			attribs.Add_many(Gfh_atr_.Bry__class, options.img_cls);
		}
		if (Php_utl_.isset(options.override_height)) {
			attribs.Add_many(Gfh_atr_.Bry__class, options.override_height);
		}
		if (Php_utl_.isset(options.override_width)) {
			attribs.Add_many(Gfh_atr_.Bry__width, options.override_height);
		}

		// Additional densities for responsive images, if specified.
		// If any of these urls is the same as src url, it'll be excluded.
//			$responsiveUrls = array_diff(this.responsiveUrls, [ this.url ]);
//			if (!Php_utl_.Empty($responsiveUrls)) {
//				$attribs['srcset'] = Html::srcSet($responsiveUrls);
//			}

		// XO.MW.HOOK:ThumbnailBeforeProduceHTML
		Xomw_xml.Element(tmp, Gfh_tag_.Bry__img, attribs, Bry_.Empty, Bool_.Y);
		Link_wrap(bfr, link_attribs_is_null ? null : link_attribs, tmp.To_bry_and_clear());
	}
	private static final    byte[] Bry__vertical_align = Bry_.new_a7("vertical-align: ");
}
