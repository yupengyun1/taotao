package com.taotao.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.EUTreeNode;
import com.taotao.common.utils.TaotaoResult;
import com.taotao.mapper.TbContentCategoryMapper;
import com.taotao.pojo.TbContentCategory;
import com.taotao.pojo.TbContentCategoryExample;
import com.taotao.pojo.TbContentCategoryExample.Criteria;
import com.taotao.service.ContenCategoryService;


/**
 * 内容分类管理
 * @author 于朋云
 *
 */
@Service
public class ContentCategoryServiceImpl implements ContenCategoryService{

	@Autowired
	private TbContentCategoryMapper categoryMapper;
	@Override
	public List<EUTreeNode> getCategoryList(long parentId) {
		TbContentCategoryExample tbContentCategoryExample = new TbContentCategoryExample();
		Criteria createCriteria = tbContentCategoryExample.createCriteria();
		createCriteria.andParentIdEqualTo(parentId);
		List<TbContentCategory> list = categoryMapper.selectByExample(tbContentCategoryExample);
		List<EUTreeNode> resultList = new ArrayList<EUTreeNode>();
		for (TbContentCategory tbContentCategory : list) {
			EUTreeNode node = new EUTreeNode();
			node.setId(tbContentCategory.getId());
			node.setText(tbContentCategory.getName());
			node.setState(tbContentCategory.getIsParent()?"closed":"open");
			resultList.add(node);
		}
		
		return resultList;
	}
	@Override
	public TaotaoResult insertContentCategory(long parentId, String name) {
		//创建一个pojo
		TbContentCategory contentCategory = new TbContentCategory();
		contentCategory.setName(name);
		contentCategory.setIsParent(false);
		//'状态。可选值:1(正常),2(删除)',
		contentCategory.setStatus(1);
		contentCategory.setParentId(parentId);
		contentCategory.setSortOrder(1);
		contentCategory.setCreated(new Date());
		contentCategory.setUpdated(new Date());
		//添加记录
		categoryMapper.insert(contentCategory);
		//查看父节点的isParent列是否为true，如果不是true改成true
		TbContentCategory parentCat = categoryMapper.selectByPrimaryKey(parentId);
		//判断是否为true
		if(!parentCat.getIsParent()) {
			parentCat.setIsParent(true);
			//更新父节点
			categoryMapper.updateByPrimaryKey(parentCat);
		}
		//返回结果
		return TaotaoResult.ok(contentCategory);

	}

}
