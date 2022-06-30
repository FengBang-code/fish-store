package com.yutang.mapper;

import com.yutang.entity.AccountStatement;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yutang.model.background.AccountQueryDTO;
import com.yutang.model.background.AccountQueryVO;
import com.yutang.model.background.AccountUpdateDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author fzhuoxing
 * @since 2022-04-14
 */

public interface AccountStatementMapper extends BaseMapper<AccountStatement> {
    List<AccountQueryVO> queryAccount(@Param("model") AccountQueryDTO model,@Param("offsetNum") long offsetNum);

    Integer updateAccount(@Param("model") AccountUpdateDTO model);
}
