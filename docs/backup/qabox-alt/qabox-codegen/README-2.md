# JpaCodeBuilder

参考 `https://github.com/moshowgame/SpringBootCodeGenerator` 把从Maven改成Gradle来构建.

# Description
- √ 基于SpringBoot2+Freemarker+Bootstrap
- √ 以解放双手为目的，减少大量重复的CRUD工作
- √ 支持mysql/oracle/pgsql三大数据库
- √ 用DDL-SQL语句生成JPA/JdbcTemplate/Mybatis/MybatisPlus/BeetlSQL相关代码.

# Advantage 
- 支持DDL SQL/INSERT SQL/SIMPLE JSON生成模式
- 自动记忆最近生成的内容，最多保留9个
- 提供众多通用模板，易于使用，复制粘贴加简单修改即可完成CRUD操作
- 支持特殊字符模板(`#`请用`井`代替;`$`请用`￥`代替)
- 根据comment=(mysql)或者comment on table(pgsql/oracle)生成类名注释
- BeanUtil提供一些基本对象的使用方法供COPY

