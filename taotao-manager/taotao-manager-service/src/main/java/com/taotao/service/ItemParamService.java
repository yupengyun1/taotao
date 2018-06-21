package com.taotao.service;


import org.springframework.stereotype.Service;

import com.taotao.common.utils.TaotaoResult;
import com.taotao.pojo.TbItemParam;

public interface ItemParamService {

	TaotaoResult getItemParamByCid(long cid );
	TaotaoResult insertItemParam(TbItemParam itemParams);
}
