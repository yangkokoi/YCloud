// /**
//  * Copyright: 2019-2020
//  * FileName: ExcelController
//  * Author:   Young
//  * Date:     2020/12/1 20:11
//  * Description:
//  * History:
//  * <author>          <time>          <version>          <desc>
//  * Young         修改时间           版本号             描述
//  */
// package cn.xiaosm.cloud.core.controller;
//
// import cn.hutool.core.io.FileUtil;
// import cn.hutool.core.io.IoUtil;
// import cn.hutool.json.JSONUtil;
// import cn.hutool.poi.excel.ExcelUtil;
// import cn.hutool.poi.excel.ExcelWriter;
// import cn.xiaosm.yadmin.core.annotation.Yadmin;
// import cn.xiaosm.yadmin.core.entity.vo.ExcelVO;
// import org.springframework.web.bind.annotation.*;
//
// import javax.servlet.ServletOutputStream;
// import javax.servlet.http.HttpServletRequest;
// import javax.servlet.http.HttpServletResponse;
// import java.io.IOException;
// import java.util.List;
//
// /**
//  * 〈一句话功能简述〉
//  * 〈〉
//  *
//  * @author Young
//  * @create 2020/12/1
//  * @since 1.0.0
//  */
// @Yadmin
// @RestController
// @RequestMapping("/excel")
// public class ExcelController {
//
//     @PostMapping
//     public void export(
//             ExcelVO excelVO,
//             HttpServletRequest request, HttpServletResponse response) {
//         String path = this.getClass().getResource("/").getPath();
//         System.out.println(path);
//         path += "/writeTest.xlsx";
//         //通过工具类创建writer
//         ExcelWriter writer = ExcelUtil.getWriter(path);
//         //跳过当前行，既第一行，非必须，在此演示用
//         writer.passCurrentRow();
//         excelVO.getColumns().forEach(el -> {
//             //自定义标题别名
//             writer.addHeaderAlias(el.getKey(), el.getValue());
//         });
//         // 仅导出设置了别名的数据
//         writer.setOnlyAlias(true);
//         //合并单元格后的标题行，使用默认标题样式
//         writer.merge(excelVO.getColumns().size() - 1, excelVO.getName());
//         writer.write(JSONUtil.parseArray(excelVO.getData()), true);
//
//         //response为HttpServletResponse对象
//         response.setContentType("application/vnd.ms-excel;charset=utf-8");
//         //test.xls是弹出下载对话框的文件名，不能为中文，中文请自行编码
//         response.setHeader("Content-Disposition",
//                 "attachment;filename=" + "excel" + ".xlsx");
//         ServletOutputStream out= null;
//         try {
//             out = response.getOutputStream();
//             writer.flush(out, true);
//         } catch (IOException e) {
//             e.printStackTrace();
//         } finally {
//             // 关闭writer，释放内存
//             writer.close();
//             //此处记得关闭输出Servlet流
//             IoUtil.close(out);
//             FileUtil.del(path);
//         }
//     }
//
//     @PostMapping("/test")
//     public String export1(@RequestParam List<String> list, HttpServletRequest request, HttpServletResponse response) {
//         System.out.println(list);
//
//         return "ok";
//     }
//
// }