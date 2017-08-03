package org.kyll.base.persistence;

import java.util.List;
import java.util.Map;

/**
 * User: Kyll
 * Date: 2014-10-27 16:41
 */
public interface Dao {
	/**
	 * 执行SQL查询语句
	 * @param sql SQL 语句
	 * @return Map<String, Object>
	 */
	Map<String, Object> queryForOne(String sql);

	/**
	 * 执行SQL查询语句
	 * @param sql SQL 语句
	 * @return Map<String, Object> 的 List 集合
	 */
	List<Map<String, Object>> queryForList(String sql);

	/**
	 * 执行非SQL查询语句
	 * @param sql SQL 语句
	 * @return
	 */
	void execute(String sql);
}
