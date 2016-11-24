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
package gplx.core.threads.utils; import gplx.*; import gplx.core.*; import gplx.core.threads.*;
import java.util.concurrent.ArrayBlockingQueue;
public class Gfo_blocking_queue {
		private final ArrayBlockingQueue queue;
		public Gfo_blocking_queue(int capacity) {
		this.capacity = capacity;
				this.queue = new ArrayBlockingQueue(capacity);
			}
	public int Capacity() {return capacity;} private final    int capacity;
	public void Put(Object o) {
				try {queue.put(o);}
		catch (InterruptedException e) {throw Err_.new_exc(e, "threads", "put interrupted");}
			}
	public Object Take() {
				try {return queue.take();}
		catch (InterruptedException e) {throw Err_.new_exc(e, "threads", "take interrupted");}
			}
}
