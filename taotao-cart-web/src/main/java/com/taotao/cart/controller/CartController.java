package com.taotao.cart.controller;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.CookieUtils;
import com.taotao.common.utils.JsonUtils;
import com.taotao.pojo.TbItem;
import com.taotao.service.ItemService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @description: 购物车Controller
 * @author:
 * @create: 2018-10-07 22:15
 **/
@Controller
public class CartController {
    @Value("${CART_KEY}")
    private String CART_KEY;
    @Value("${CART_EXPIRE}")
    private Integer CART_EXPIRE;

    @Autowired
    private ItemService itemService;


    /**
     * 添加商品到购物车
     *
     * @param itemId
     * @param num
     * @param request
     * @return
     */
    @RequestMapping(value = "/cart/add/{itemId}")
    public String addItemToCart(@PathVariable Long itemId, @RequestParam(defaultValue = "1") Integer num, HttpServletRequest request, HttpServletResponse response) {
        //从cookie中获取购物车
        List<TbItem> cartList = getCartList(request);

        //判断商品在商品列表中是否存在。
        boolean flag = false;
        //通过商品id判断商品是否存在，若存在，则改变商品数量
        for (TbItem item : cartList) {
            if (item.getId() == itemId.longValue()) {
                item.setNum(item.getNum() + num);
                flag = true;
                break;
            }
        }
        //若不存在，则将商品加入购物车
        if (!flag) {
            TbItem tbItem = itemService.getItemById(itemId);
            //图片要处理一下，取一张就行
            String imgStr = tbItem.getImage();
            if (StringUtils.isNotBlank(imgStr)) {
                String[] imgs = imgStr.split(",");
                tbItem.setImage(imgs[0]);
            }
            //设置购买数量
            tbItem.setNum(num);

            cartList.add(tbItem);
        }

        //将购物车存入cookie
        CookieUtils.setCookie(request, response, CART_KEY, JsonUtils.objectToJson(cartList), CART_EXPIRE, true);

        return "cartSuccess";
    }

    /**
     * 从cookie中获取购物车
     *
     * @return
     */
    private List<TbItem> getCartList(HttpServletRequest request) {
        String cartJson = CookieUtils.getCookieValue(request, CART_KEY, true);
        if (StringUtils.isNotBlank(cartJson)) {
            List<TbItem> cartList = JsonUtils.jsonToList(cartJson, TbItem.class);
            return cartList;
        }

        return new ArrayList<>();
    }

    /**
     * 展示购物车
     *
     * @return
     */
    @RequestMapping(value = "/cart/cart")
    public String showCart(HttpServletRequest request) {
        //从cookie中获取购物车
        List<TbItem> cartList = getCartList(request);

        request.setAttribute("cartList", cartList);

        return "cart";
    }

    /**
     * 增加(减少)商品数量 +1 或 -1,cookie中的也要改变
     *
     * @return
     */
    @RequestMapping(value = "/cart/update/num/{itemId}/{num}")
    @ResponseBody
    public TaotaoResult updateNum(@PathVariable Long itemId, @PathVariable Integer num, HttpServletRequest request, HttpServletResponse response) {
        List<TbItem> cartList = getCartList(request);
        for (TbItem item : cartList) {
            if (item.getId() == itemId.longValue()) {
                item.setNum(num);
                break;
            }
        }
        CookieUtils.setCookie(request, response, CART_KEY, JsonUtils.objectToJson(cartList), CART_EXPIRE, true);

        return TaotaoResult.ok();
    }

    /**
     * 删除购物车中的商品,cookie中的也要删除，然后重定向到展示购物车的.do。
     *
     * @return
     */
    @RequestMapping(value = "/cart/delete/{itemId}")
    public String deleteItem(@PathVariable Long itemId, HttpServletRequest request, HttpServletResponse response) {
        List<TbItem> cartList = getCartList(request);
        for (TbItem item : cartList) {
            if (item.getId() == itemId.longValue()) {
                cartList.remove(item);
                break;
            }
        }
        CookieUtils.setCookie(request, response, CART_KEY, JsonUtils.objectToJson(cartList), CART_EXPIRE, true);

        return "redirect:/cart/cart.html";
    }
}
