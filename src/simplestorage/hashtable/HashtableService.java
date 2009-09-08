/*
Copyright (C) 2009 Jiangyan Xu <jiangyan@ufl.edu>, University of Florida
 
This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.
 
This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
GNU General Public License for more details.
 
You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
*/

package simplestorage.hashtable;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
/**
 * Service class for the Hashtable.
 * @author Jiangyan Xu <jiangyan@ufl.edu>
 *
 */
public class HashtableService {
  public void put(String key, byte[] value, String userInfo) throws UnsupportedEncodingException {
    HashtableEntry entry = new HashtableEntry(key, value, userInfo);
    PersistenceManager pm = PMF.get().getPersistenceManager();
    try {
      pm.makePersistent(entry);
    } finally {
      pm.close();
    }
  }
  
  public HashtableGetResult get(String key) {
    PersistenceManager pm = PMF.get().getPersistenceManager();
    Query query = pm.newQuery(HashtableEntry.class);
    query.setFilter("key == keyParam");
    query.declareParameters("String keyParam");
    try {
      List<HashtableEntry> entries = (List<HashtableEntry>)query.execute(key);
      List<byte[]> values = new ArrayList<byte[]>();
      for(HashtableEntry entry : entries) {
        values.add(entry.getValue());
      }
      return new HashtableGetResult(values);
    } finally {
      pm.close();
    }
  }
}
