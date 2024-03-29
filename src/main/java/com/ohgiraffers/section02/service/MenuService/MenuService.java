package com.ohgiraffers.section02.service.MenuService;

import com.ohgiraffers.section02.service.model.dao.MenuDAO;
import com.ohgiraffers.section02.service.model.dto.CategoryDTO;
import com.ohgiraffers.section02.service.model.dto.MenuDTO;

import java.sql.Connection;

import static com.ohgiraffers.common.JDBCTemplate.*;

public class MenuService {
    /* 신규 카테고리 등록 후 신규 카테고리 코드로 신규 메뉴를 등록하는 기능 */
    public void registNewMenu() {

        /* 1. Connection 생성 */
        Connection con = getConnection();

        /* 2. DAO 메소드 호출 */
        MenuDAO menuDAO = new MenuDAO();

        /* 2-1. 카테고리 등록 */
        CategoryDTO newCategory = new CategoryDTO();
        newCategory.setName("기타");
        newCategory.setRefCategoryCode(null);

        int result1 = menuDAO.insetNewCategory(con, newCategory);

        /* 2-2. 방금 입력 된 카테고리의 코드 조회 */
        int newCategoryCode = menuDAO.selectLastCategoryCode(con);

        /* 2-3. 메뉴 등록 */
        MenuDTO newMenu = new MenuDTO();
        newMenu.setName("메롱메롱스튜");
        newMenu.setPrice(40000);
        newMenu.setCategoryCode(newCategoryCode);
        newMenu.setOrderableStatus("Y");

        int result2 = menuDAO.insertNewMenu(con, newMenu);

        /* 3. 트랜잭션 제어 */
        if(result1 > 0 && result2 > 0) {
            commit(con);
            System.out.println("신규 카테고리 등록과 신규 메뉴 등록이 완료 되었습니다.");
        } else {
            rollback(con);
            System.out.println("신규 카테고리 등록과 신규 네뮤 등록에 실패하였습니다.");
        }

        /* 4. Connection 반납 */
        close(con);
    }

}
