package kincolle;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.BytesRef;
import org.elasticsearch.index.fielddata.IndexNumericFieldData;

import java.io.IOException;
import java.nio.file.Paths;


public class Index {


    public static void main(String[] args) throws IOException {
        String a = null;
        IndexTest();
        System.out.println(Integer.BYTES);
    }

    /**
     * 创建索引
     */
    public static void IndexTest() throws IOException {

        Document doc = new Document();
//        doc.add(new LongField("id", 1, Field.Store.YES));
        doc.add(new TextField("title", "如何学习lucene", Field.Store.YES));
        Document doc2 = new Document();
        doc2.add(new TextField("content", "掌握原理，熟悉API，多看文档", Field.Store.YES));
        Document doc3 = new Document();
        doc3.add(new IntPoint("int", 1));

        int[] min = new int[]{1};
        int[] max = new int[]{2};
        doc.add(new IntRange("IntRange", min,max));
        doc.add(new IntRangeDocValuesField("IntRangeDocValuesField", min,max));
        doc.add(new SortedNumericDocValuesField("SortedNumericDocValuesField", 1));

        // 字段content
        String name = "content";
        String value = "张三说的确实在理";
        FieldType type = new FieldType();
        // 设置是否存储该字段
        type.setStored(true); // 请试试不存储的结果
        // 设置是否对该字段分词
        type.setTokenized(true); // 请试试不分词的结果
        // 设置该字段的索引选项
        type.setIndexOptions(IndexOptions.DOCS_AND_FREQS_AND_POSITIONS_AND_OFFSETS); // 请尝试不同的选项的效果
        type.freeze(); // 使不可更改

        Field field = new Field(name, value, type);
        doc.add(field);







        //数值存储例子
        FieldType num=new FieldType();
        num.setStored(true);//设置存储
        num.setIndexOptions(IndexOptions.DOCS);//设置索引类型
        num.setDocValuesType(DocValuesType.NUMERIC);//DocValue类型

        //添加string字段
        doc.add(new SortedDocValuesField("id",new BytesRef("01011")));
        //添加数值类型的字段  Float,Doule需要额外转成bit位才能存储，Interger和Long则不需要
        doc.add(new DoubleDocValuesField("price", Double.doubleToRawLongBits(25.258)));








        Directory dir = FSDirectory.open(Paths.get("D:\\lucene"));

        StandardAnalyzer analyzer = new StandardAnalyzer();
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        IndexWriter index = new IndexWriter(dir, config);
        index.addDocument(doc);

        Term a = new Term("");
        index.deleteDocuments(a);

        index.forceMerge(1);
        index.flush();
        index.maybeMerge();
        index.commit();
        index.close();
    }
}
