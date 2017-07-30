package org.kyll.base.persistence;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * User: Kyll
 * Date: 2014-10-27 16:41
 * @param <P> 主键
 * @param <E> 数据库实体
 */
public interface Dao<P extends Serializable, E extends Entity> {
	/**
	 * 根据主键获取实体。如果没有相应的实体，返回 null。
	 * @param id 主键
	 * @return 实体
	 */
	public E get(P id);

	/**
	 * 获取全部实体。
	 * @return 实体 LIST
	 */
	public List<E> getAll();

	/**
	 * 存储实体到数据库
	 * @param entity 实体
	 */
	public void save(E entity);

	/**
	 * 更新实体
	 * @param entity 实体
	 */
	public void update(E entity);

	/**
	 * 增加或更新实体
	 * @param entity 实体
	 */
	public void saveOrUpdate(E entity);

	/**
	 * 批量保存
	 * @param entities 实体数组
	 */
	@SuppressWarnings("unchecked")
	public void batchSave(E... entities);

	/**
	 * 批量保存
	 * @param entities 实体集合
	 */
	public void batchSave(Collection<E> entities);

	/**
	 * 批量更新
	 * @param entities 实体数组
	 */
	@SuppressWarnings("unchecked")
	public void batchUpdate(E... entities);

	/**
	 * 批量更新
	 * @param entities 实体集合
	 */
	public void batchUpdate(Collection<E> entities);

	/**
	 * 删除指定的实体
	 * @param ids 主键
	 */
	@SuppressWarnings("unchecked")
	public void delete(P... ids);

	/**
	 * 删除指定的实体
	 * @param entity 实体
	 */
	public void delete(E entity);

	/**
	 * 删除指定的实体
	 * @param entities 实体
	 */
	public void delete(Collection<E> entities);

	/**
	 * 执行SQL查询语句
	 * @param sql SQL 语句
	 * @return Map<String, Object>
	 */
	public Map<String, Object> queryForOne(String sql);

	/**
	 * 执行SQL查询语句
	 * @param sql SQL 语句
	 * @return Map<String, Object> 的 List 集合
	 */
	public List<Map<String, Object>> queryForList(String sql);

	/**
	 * 执行非SQL查询语句
	 * @param sql SQL 语句
	 * @return
	 */
	public void execute(String sql);
}
