package com.itheima.Lucene;

import com.sun.xml.internal.ws.developer.UsesJAXBContext;
import org.apache.commons.io.FileUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.document.*;
import org.apache.lucene.index.*;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.RAMDirectory;

import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;
import java.io.IOException;

public class lucenFirst {

    @Test
    public void createIndex() throws Exception {
        //1、创建一个Director对象，指定索引库保存的位置。
        //把索引库保存在内存中
        //Directory directory = new RAMDirectory();
        //把索引库保存在磁盘
        Directory directory = FSDirectory.open(new File("D:\\temp\\index").toPath());
        //2、基于Directory对象创建一个IndexWriter对象
        IndexWriterConfig config = new IndexWriterConfig(new IKAnalyzer());
        IndexWriter indexWriter = new IndexWriter(directory, config);
        //3、读取磁盘上的文件，对应每个文件创建一个文档对象。
        File dir = new File("E:\\李乐黑马视频全套\\第四阶段\\第四阶段\\61.会员版(2.0)-就业课(2.0)-Lucene\\lucene\\02.参考资料\\searchsource");
        File[] files = dir.listFiles();
        for (File f :
                files) {
            //取文件名
            String fileName = f.getName();
            //文件的路径
            String filePath = f.getPath();
            //文件的内容
            String fileContent = FileUtils.readFileToString(f, "utf-8");
            //文件的大小
            long fileSize = FileUtils.sizeOf(f);
            //创建Field
            //参数1：域的名称，参数2：域的内容，参数3：是否存储
            Field fieldName = new TextField("name", fileName, Field.Store.YES);
            //Field fieldPath = new TextField("path", filePath, Field.Store.YES);
            Field  fieldPath = new StoredField("path", filePath);

            Field fieldContent = new TextField("content", fileContent, Field.Store.YES);
            //Field fieldSize = new TextField("size",fileSize+"" , Field.Store.YES);
            LongPoint fieldSizevalue = new LongPoint("size", fileSize);

            Field  size = new StoredField("size", fileSize);

            //创建文档对象
            Document document = new Document();
            //向文档对象中添加域
            document.add(fieldName);
            document.add(fieldPath);
            document.add(fieldContent);
            document.add(fieldSizevalue);
            document.add(size);

            //5、把文档对象写入索引库
            indexWriter.addDocument(document);
        }
        //6、关闭indexwriter对象
        indexWriter.close();
    }





    @Test
    public void searchIndex() throws IOException {


        //1、创建一个Director对象，指定索引库保存的位置。
        //把索引库保存在磁盘
        Directory directory = FSDirectory.open(new File("D:\\temp\\index").toPath());
        //2、基于Directory对象创建一个IndexReader对象
        DirectoryReader indexReader = DirectoryReader.open(directory);
        //3. 创建一个IndexSearch 对象， 构造方法中的参数indexReader对象
        IndexSearcher indexSearcher = new IndexSearcher(indexReader);
        //4.创建一个    query独享termquery
        Query query = new TermQuery(new Term("content","spring"));
        //5.执行查询，得到一个Topdorcs 对象
        TopDocs search = indexSearcher.search(query, 10);

         //取查询的记录数
        System.out.println("查询的记录数："+search.totalHits);
        //取文档列表
         ScoreDoc [] s = search.scoreDocs;
         //打印文档中的内容
        for (ScoreDoc doc : s) {
               int did = doc.doc;
            //取文档的id对象
            Document doc1 = indexSearcher.doc(did);
            System.out.println(doc1.get("name"));
            System.out.println(doc1.get("path"));
            System.out.println(doc1.get("size"));
          //  System.out.println(doc1.get("conternt"));
            System.out.println("------------------------------------------");

        }

       //关闭indexReader
        indexReader.close();
    }






    @Test
    public void testTokenStream() throws Exception {
        //1）创建一个Analyzer对象，StandardAnalyzer对象
        //Analyzer analyzer = new StandardAnalyzer();


        Analyzer analyzer = new IKAnalyzer();

        //2）使用分析器对象的tokenStream方法获得一个TokenStream对象
        TokenStream tokenStream = analyzer.tokenStream("",
                "2017年12月14日 - 传智播客Lucene概述公安局Lucene是一款高性能的、" +
                        "可扩展的信息检索(IR)工具库。信息检索是指文档搜索、文档内信息搜索或者文档相关的元数据搜索等操作。");


        //3）向TokenStream对象中设置一个引用，相当于数一个指针
        CharTermAttribute charTermAttribute = tokenStream.addAttribute(CharTermAttribute.class);


        //4）调用TokenStream对象的rest方法。如果不调用抛异常
        tokenStream.reset();


        //5）使用while循环遍历TokenStream对象
        while(tokenStream.incrementToken()) {
            System.out.println(charTermAttribute.toString());
        }
        //6）关闭TokenStream对象
        tokenStream.close();
    }
    //config
}
