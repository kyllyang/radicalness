package org.kyll.base.persistence;

import org.kyll.common.paginated.Dataset;
import org.kyll.common.paginated.Paginated;
import org.kyll.common.paginated.Sort;

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
public interface Dao<E extends Entity, P extends Serializable> {
	/**
	 * 根据主键获取实体。如果没有相应的实体，返回 null。
	 * @param id 主键
	 * @return 实体
	 */
	E get(P id);

	/**
	 * 获取全部实体
	 * @return 实体 LIST
	 */
	List<E> getAll();

	/**
	 * 按条件查询数据
	 * @param condition 条件对象
	 * @param sorts 排序
	 * @return 实体 List
	 */
	List<E> find(Condition condition, Sort... sorts);

	/**
	 * 按条件查询数据
	 * @param condition 条件对象
	 * @param sortList 排序
	 * @return 实体 List
	 */
	List<E> find(Condition condition, List<Sort> sortList);

	/**
	 * 按条件查询数据并分页
	 * @param condition 条件对象
	 * @param paginated 分页对象
	 * @return 带有分页信息的数据集
	 */
	Dataset<E> find(Condition condition, Paginated paginated);

	/**
	 * 存储实体到数据库
	 * @param entity 实体
	 */
	void insert(E entity);

	/**
	 * 更新实体
	 * @param entity 实体
	 */
	void update(E entity);

	/**
	 * 插入或者更新实体
	 * @param entity 实体
	 */
	void save(E entity);
	/**
	 * 批量保存
	 * @param entities 实体数组
	 */
	@SuppressWarnings("unchecked")
	void batchInsert(E... entities);

	/**
	 * 批量保存
	 * @param entities 实体集合
	 */
	void batchInsert(Collection<E> entities);

	/**
	 * 批量更新
	 * @param entities 实体数组
	 */
	@SuppressWarnings("unchecked")
	void batchUpdate(E... entities);

	/**
	 * 批量更新
	 * @param entities 实体集合
	 */
	void batchUpdate(Collection<E> entities);

	/**
	 * 删除指定的实体
	 * @param ids 主键
	 */
	@SuppressWarnings("unchecked")
	void delete(P... ids);

	/**
	 * 删除指定的实体
	 * @param entity 实体
	 */
	void delete(E entity);

	/**
	 * 删除指定的实体
	 * @param entities 实体
	 */
	void delete(Collection<E> entities);

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
