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
import gplx.texts.*; /*CharStream*/
public class GfmlTrie {
	public String[] Symbols() {
		String[] rv = new String[symbols.Count()];
		for (int i = 0; i < rv.length; i++)
			rv[i] = String_.cast_(symbols.Get_at(i));
		return rv;
	}	Ordered_hash symbols = Ordered_hash_.new_();
	public int LastMatchCount; // PERF: prop is faster than method
	public Object FindMatch(CharStream stream) {
		Object result = null; int moveCount = 0; LastMatchCount = 0;
		IntObjHash_base link = rootLink;
		while (stream.AtMid()) {
			Object found = link.Get_by(stream.Cur());
			if (found == null) break;										//	found is null; can happen for false matches; ex: <!-- reg, and <!-* is cur; goes to <!- before exit
			LastMatchCount++;
			IntObjHash_base foundAsLink = IntObjHash_base_.as_(found);
			if (foundAsLink != null) {										//	if (found is link) (ie: another link exists)
				result = foundAsLink.Bay();									//		set .Bay as possible result; needed for short-long pairs; ex: < and <!-- exist; < found, but need to check next symbol for !
				link = foundAsLink;											//		make foundAsLink the current one
				moveCount++;
				stream.MoveNext();
			}
			else {															//	not a link; must be last
				result = found;
				break;
			}
		}
		if (moveCount > 0) stream.MoveBackBy(moveCount);					//	restore stream to original position; imitate idempotency
		return result;
	}
	public void Add(String symbol, Object data) {
		if (data == null) throw Err_.new_wo_type("null objects cannot be registered", "symbol", symbol);

		char[] ary = String_.XtoCharAry(symbol); int lastIndex = ary.length - 1;
		IntObjHash_base curLink = rootLink;
		for (int i = 0; i < ary.length; i++) {
			char c = ary[i];
			Object found = curLink.Get_by(c);
			IntObjHash_base foundAsLink = IntObjHash_base_.as_(found);
			if (i == lastIndex) {											//	lastChar
				if (found != null) {										//		slot is occupied
					if (foundAsLink != null)								//			found is link; occurs when symbol is shorter than existing: ex: adding '<' when '<!--' exists)
						foundAsLink.Bay_set(data);							//				place found in link's bay
					else													//			found is makr; occurs when symbol is the same as existing; ex: adding '<!' when '<!' exists
						curLink.Set(c, data);								//				place found in slot (replaces slot)
				}
				else														//		slot is unoccupied
					curLink.Add(c, data);									//			place found in slot
			}
			else {															//	not lastChar
				if (foundAsLink == null) {									//		next link does not exist (NOTE: next link is necessary, since char is not lastChar)
					foundAsLink = IntObjHash_base_.new_();						//		create next link
					if (found != null) {									//		slot is occupied; occurs when symbol is longer than existing: ex: adding '<!--' when '<' exists
						foundAsLink.Bay_set(found);							//			transplant occupied found to link's Bay 
						curLink.Set(c, foundAsLink);						//			place new link in slot
					}
					else													//		slot is unoccupied
						curLink.Add(c, foundAsLink);						//			place found in slot
				}
				curLink = foundAsLink;
			}
		}
		symbols.Add_if_dupe_use_nth(symbol, symbol);
	}
	public void Del(String symbol) {
		char[] ary = String_.XtoCharAry(symbol); int lastIndex = ary.length - 1;
		IntObjHash_base[] linkAry = new IntObjHash_base[ary.length];		//	first, get linkAry -- one link for each symbol
		IntObjHash_base link = rootLink;
		for (int i = 0; i < ary.length; i++) {
			char c = ary[i];
			linkAry[i] = link;
			link = IntObjHash_base_.as_(link.Get_by(c));
			if (link == null) break;										//		c does not have nextHash; break
		}

		IntObjHash_base nextHash = null;
		for (int i = lastIndex; i >= 0; i--) {								//	remove each char from hashes; must move backwards
			char c = ary[i];
			IntObjHash_base curLink = linkAry[i];
			Object found = curLink.Get_by(c);
			IntObjHash_base foundAsLink = IntObjHash_base_.as_(found);
			if (nextHash != null && nextHash.Bay() != null)					//		occurs when long is dropped; ex: '<-' and '<'; '<-' dropped; <'s .Bay in '<-' chain must be transplanted to '<' .Bay
				curLink.Set(c, nextHash.Bay());
			else if (foundAsLink != null && foundAsLink.Bay() != null)		//		occurs when short is dropped; ex: '<-' and '<'; '<' dropped; <'s .Bay must be transplanted to '<' .Bay in '<-' chain 
				foundAsLink.Bay_set(found);
			else															//		no long/short overlap; simply remove
				curLink.Del(c);
			nextHash = curLink;
		}
		symbols.Del(symbol);
	}
	public void Clear() {rootLink.Clear();}
	IntObjHash_base rootLink = IntObjHash_base_.new_();
	public static GfmlTrie new_() {return new GfmlTrie();}
}
