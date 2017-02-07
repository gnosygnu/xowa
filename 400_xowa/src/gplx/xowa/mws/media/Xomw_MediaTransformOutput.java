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
import gplx.langs.phps.utls.*;
import gplx.xowa.mws.filerepo.file.*;
public class Xomw_MediaTransformOutput {
//		private final    Xomw_File file;
	private final    byte[] url;
	private final    int width, height;
	private final    List_adp attribs = List_adp_.New(), link_attribs = List_adp_.New();
	public Xomw_MediaTransformOutput(Xomw_File file, byte[] url, byte[] path, int width, int height) {
//			this.file = file;
		this.url = url;
		this.width = width;
		this.height = height;
	}
	/**
	* @return int Width of the output box
	*/
	public int getWidth() {
		return this.width;
	}

	/**
	* @return int Height of the output box
	*/
	public int getHeight() {
		return this.height;
	}

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
	//     desc-query   String, description link query params
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
	public void To_html(Bry_bfr bfr, Bry_bfr tmp, Xomw_MediaTransformOutputParams options) {
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
		if (Php_utl_.Is_set(options.override_height)) {
			attribs.Add_many(Gfh_atr_.Bry__class, options.override_height);
		}
		if (Php_utl_.Is_set(options.override_width)) {
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
	// Wrap some XHTML text in an anchor tag with the given attributes
	// XO.MW:SYNC:1.29; DATE:2017-02-03
	private void Link_wrap(Bry_bfr bfr, List_adp link_attribs, byte[] contents) {
		if (link_attribs != null) {
			Xomw_xml.Tags(bfr, Gfh_tag_.Bry__a, link_attribs, contents);
		}
		else {
			bfr.Add(contents);
		}
	}
	private static final    byte[] Bry__vertical_align = Bry_.new_a7("vertical-align: ");
}
