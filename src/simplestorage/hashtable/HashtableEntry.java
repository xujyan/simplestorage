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

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.jdo.annotations.IdentityType;

import com.google.appengine.api.datastore.Blob;


/**
 * @author Jiangyan Xu <jiangyan@ufl.edu>
 * 
 */
@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class HashtableEntry {
  @PrimaryKey
  @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
  private Long id;
  /**
   * String type for key so it's easier to see from web console. 
   */
  @Persistent
  private String key;
  @Persistent
  private Blob value;
  @Persistent
  private Date timestamp;
  @Persistent
  private String userInfo;
  /**
   * @return the key
   */
  public String getKey() {
    return key;
  }
  /**
   * @param key the key to set
   */
  public void setKey(String key) {
    this.key = key;
  }
  /**
   * @return the value
   */
  public byte[] getValue() {
    return value.getBytes();
  }
  /**
   * @param value the value to set
   */
  public void setValue(byte[] value) {
    this.value = new Blob(value);
  }
  /**
   * @return the timestamp
   */
  public Date getTimestamp() {
    return timestamp;
  }
  /**
   * @param timestamp the timestamp to set
   */
  public void setTimestamp(Date timestamp) {
    this.timestamp = timestamp;
  }
  
  /**
   * @return the userInfo
   */
  public String getUserInfo() {
    return userInfo;
  }
  /**
   * @param userInfo the userInfo to set
   */
  public void setUserInfo(String userInfo) {
    this.userInfo = userInfo;
  }
  /**
   * @return the id
   */
  public Long getId() {
    return id;
  }
  
  public HashtableEntry(String key, byte[] value, String userInfo) {
    this.key = key;
    this.value = new Blob(value);
    this.timestamp = new Date();
    this.userInfo = userInfo;
  }
}
