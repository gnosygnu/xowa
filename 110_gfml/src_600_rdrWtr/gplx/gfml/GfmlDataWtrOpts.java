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
package gplx.gfml; import gplx.*;
public class GfmlDataWtrOpts {
	public static final String Key_const = "GfmlDataWtrOpts";
	public String KeyedSpr() {return keyedSeparator;} public GfmlDataWtrOpts KeyedSeparator_(String val) {keyedSeparator = val; return this;} private String keyedSeparator = " ";
	public boolean IndentNodes() {return indentNodes;} public GfmlDataWtrOpts IndentNodesOn_() {indentNodes = true; return this;} private boolean indentNodes;
	public boolean IgnoreNullNames() {return ignoreNullNames;} public GfmlDataWtrOpts IgnoreNullNamesOn_() {ignoreNullNames = true; return this;} private boolean ignoreNullNames;
        public static final GfmlDataWtrOpts _ = new GfmlDataWtrOpts();
	public static GfmlDataWtrOpts new_() {return new GfmlDataWtrOpts();} GfmlDataWtrOpts() {}
	public static GfmlDataWtrOpts cast_(Object obj) {try {return (GfmlDataWtrOpts)obj;} catch(Exception exc) {throw Exc_.new_type_mismatch_w_exc(exc, GfmlDataWtrOpts.class, obj);}}
}
