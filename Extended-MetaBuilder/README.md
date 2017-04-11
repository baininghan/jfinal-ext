编辑_JFinalDemoGenerator.java文件，覆盖Generator默认的MetaBuilder为自己的扩展类MetaBuilderExtend
```
MetaBuilderExtend metaBuilder = new MetaBuilderExtend(getDataSource());
metaBuilder.addProcessedTable("table1","table2");// 添加需要生成的表
```