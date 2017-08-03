package org.kyll.base.persistence;

import org.kyll.common.paginated.Dataset;
import org.kyll.common.paginated.Paginated;
import org.kyll.common.paginated.Sort;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * User: Kyll
 * Date: 2017-08-03 16:46
 */
public interface EntityDao<E extends Entity, P extends Serializable> {
	/**
	 * 根据主键获取实体。如果没有相应的实体，返回 null。
	 * @param id 主键
	 * @return 实体
	 */
	E get(P id);

	/**
	 * 获取全部数据
	 * @return 实体 LIST
	 */
	List<E> getAll();

	/**
	 * 获取一个实体
	 * @param fieldName 实体属性名
	 * @param fieldValue 属性值
	 * @return 实体
	 */
	E get(String fieldName, String fieldValue);

	/**
	 * 按条件查询数据
	 * @param fieldName 实体属性名
	 * @param fieldValue 属性值
	 * @return 实体 LIST
	 */
	List<E> find(String fieldName, String fieldValue);

	/**
	 * 按条件查询数据
	 * @param fieldName 实体属性名
	 * @param fieldValue 属性值
	 * @param sorts 排序
	 * @return 实体 LIST
	 */
	List<E> find(String fieldName, String fieldValue, Sort... sorts);

	/**
	 * 按条件查询数据
	 * @param fieldName 按条件查询数据
	 * @param fieldValue 属性值
	 * @param sortList 排序
	 * @return 实体 LIST
	 */
	List<E> find(String fieldName, String fieldValue, List<Sort> sortList);

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

}
