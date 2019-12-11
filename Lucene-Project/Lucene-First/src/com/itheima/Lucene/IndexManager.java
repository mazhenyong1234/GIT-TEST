package com.itheima.Lucene;/*
author:ma
*/

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StoredField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.FSDirectory;
import org.junit.Before;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;
import java.io.IOException;

public class IndexManager {

    private IndexWriter indexWriter;

    @Before
    public void init()throws Exception{
        //创建一个INdexwriter对象，需要使用IKAnalyzer作为分析器
        IndexWriter indexWriter = new IndexWriter(
                FSDirectory.open(new File("D:\\temp\\index").toPath()),
                new IndexWriterConfig(new IKAnalyzer())
        );
    }

    @Test
    public void addDocument()throws Exception{
        //创建一个INdexwriter对象，需要使用IKAnalyzer作为分析器

        //创建一个document 对象中
        Document document = new Document();

        //向document对象中添加域
        document.add(new TextField("name","新添加的文件", Field.Store.YES));
        document.add(new TextField("content","新添加的文件内容", Field.Store.NO));
        document.add(new StoredField("path",  "D:/temp/helo"));

        //把文档写入索引库
        indexWriter.addDocument(document);
        //关闭索引库

        indexWriter.close();


    }



    @Test
    public void deleteall() throws IOException {
        //删除全部文档
        indexWriter.deleteAll();
        //关闭索引库
        indexWriter.close();
    }



}
