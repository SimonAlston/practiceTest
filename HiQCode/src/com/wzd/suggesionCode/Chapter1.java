package com.wzd.suggesionCode;


import org.junit.Test;

import javax.script.*;
import javax.tools.*;
import java.io.*;
import java.lang.reflect.Method;
import java.net.URI;
import java.text.NumberFormat;
import java.util.*;

import static java.lang.Integer.parseInt;
import static java.text.NumberFormat.getInstance;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * @author wangzhengdong
 * @date 2019年04月22日
 * @description 包名全小写，类名首字母全大写，常量全部大写并用下划线分隔，变量采用驼峰命名法（Camel Case）命名
 */
public class Chapter1 implements Serializable {


    //字母"l"作为长整型标志时务必大写
    @Test
    public void suggestion1() {
        long i = 1l;//error
        long c = 1L;//correct
        System.out.println("i的两倍是：" + i * 2);
        System.out.println("c的两倍是：" + c * 2);
    }

    //务必让常量的值在运行期保持不变。
    public static final int RAND_CONST = new Random().nextInt();//error

    @Test
    public void suggestion2() {
        System.out.println("常量会变哦：" + RAND_CONST);
    }

    //务必使三元表达式【三元操作符】的类型保持一致
    @Test
    public void suggestion3() {
        int i = 80;
        String s = String.valueOf(i < 100 ? 90 : 100);
        String s1 = String.valueOf(i < 100 ? 90 : 100.0);
        System.out.println("两者是否相等：" + s.equals(s1));
    }


    //if user overload  --> 虚拟机会根据Method Signature 来判断使用哪个方法
    @Test
    public void suggestion4() {
        //499元的货物,打75折
        this.calPrice(49900, 75);
    }

    //简单折扣计算
    public void calPrice(int price, int discount) {
        float knockdownPrice = price * discount / 100.0F;
        System.out.println("简单折扣后的价格是:" + formateCurrency(knockdownPrice));
    }

    //复杂多折扣计算
    public void calPrice(int price, int... discounts) {
        float knockdownPrice = price;
        for (int discount : discounts) {
            knockdownPrice = knockdownPrice * discount / 100;
        }
        System.out.println("复杂折扣后的价格是:" + formateCurrency(knockdownPrice));
    }

    //格式化成本的货币形式
    private String formateCurrency(float price) {
        return NumberFormat.getCurrencyInstance().format(price / 100);
    }

    //在overload 当中有多个变长方法的时候, 传入空值或null,因为未指定类型, 所以虚拟机会找不到用那个方法
    //KSS原则 Keep It Simple, Stupid，即懒人原则
    @Test
    public void suggestion5() {
        Chapter1 client = new Chapter1();
        client.methodA("China", 0);
        client.methodA("China", "People");
        //error
//        client.methodA("China");
//        client.methodA("China", null);

        //current
        Integer[] in = null;
        client.methodA("China", in);
        String[] str = null;
        client.methodA("China", str);
    }

    public void methodA(String str, Integer... is) {
    }

    public void methodA(String str, String... strs) {
    }

    //使用多态[左父右子] 的时候, 编译看左, 运行看右
    @Test
    public void suggestion6() {
        //向上转型
        Base base = new Sub();
        base.fun(100, 50);
        //不转型
        Sub sub = new Sub();
//        sub.fun(100,50);
    }

    //基类
    class Base {
        void fun(int price, int... discounts) {
            System.out.println("Base...fun");
        }
    }

    //子类,覆写父类方法
    class Sub extends Base {
        @Override
        void fun(int price, int[] discounts) {
            System.out.println("Sub...fun");

        }
    }

    //后自增表达式, 是返回自增前的数

    /**
     * 步骤1　JVM把count值（其值是0）拷贝到临时变量区。
     * <p>
     * 步骤2　count值加1，这时候count的值是1。
     * <p>
     * 步骤3　返回临时变量区的值，注意这个值是0，没修改过。
     * <p>
     * 步骤4　返回值赋值给count，此时count值被重置成0。
     */

    //C++中"count=count++"与"count++"是等效的，而在PHP中则保持着与Java相同的处理方式
    @Test
    public void suggestion7() {
        int count = 0;
        for (int i = 0; i < 10; i++) {
            count++;
        }
        System.out.println("count=" + count);
    }

    // : 这里的是goto标号语言  goto语句标号:
    //C语言才有的语句, goto语言有危害, 应尽量避免使用-->会造成程序混乱
    //其实continue break 也是来源于goto语句
    @Test
    public void suggestion8() {
        //数据定义及初始化
        int fee = 200;
        //其他业务处理
        saveDefault:
        save(fee);
        //其他业务处理
    }

    static void saveDefault() {
    }

    static void save(int fee) {
    }


    /**
     * 对于静态导入，一定要遵循两个规则:
     * <p>
     * 不使用*（星号）通配符，除非是导入静态常量类（只包含常量的类或接口）。
     * <p>
     * 方法名是具有明确、清晰表象意义的工具类。
     */
    @Test
    public void suggestion9() {
        //断言
        assertEquals("foo", "foo");
        assertFalse(Boolean.FALSE);
    }

    //计算圆面积
    public static double calCircleArea(double r) {
        return Math.PI * r * r;
    }

    //计算球面积
    public static double calBallArea(double r) {
        return 4 * Math.PI * r * r;
    }

    //计算圆面积
    public static double calCircleArea2(double r) {
//        return PI*r*r;
        return 0;
    }

    //计算球面积
    public static double calBallArea2(double r) {
//        return 4*PI*r*r;
        return 0;
    }

    //输入半径和精度要求,计算面积
    @Test
    public void suggestion9_1() {
        String[] args = {"0.00", "0.00"};
//        double s=PI*parseDouble(args[0]);
        NumberFormat nf = getInstance();
        nf.setMaximumFractionDigits(parseInt(args[1]));
//        formatMessage(nf.format(s));
    }

    //格式化消息输出
    public static void formatMessage(String s) {
        System.out.println("圆面积是:" + s);
    }


    /**
     * 不要在本类中覆盖静态导入的变量和方法
     * <p>
     * 编译器有一个“最短路径”原则：如果能够在本类中查找到的变量、常量、方法，就不会到其他包或父类、接口中查找，以确保本类中的属性、方法优先
     */
    @Test
    public void suggestion10() {
        System.out.println("PI=" + PI);
        System.out.println("abs(100)=" + abs(-100));
    }

    //常量名与静态导入的PI相同
    public final static String PI = "祖冲之";

    //方法名与静态导入的相同
    public static int abs(int abs) {
        return 0;
    }


    /**
     * ----------------------suggestion11
     * <p>
     * 第一点 : 流标识符（Stream Unique Identifier）  serialVersionUID  养成良好习惯，显式声明UID
     * <p>
     * 隐式声明则是我不声明，你编译器在编译的时候帮我生成。
     * 生成的依据是通过包名、类名、继承关系、非私有的方法和属性，以及参数、返回值等诸多因子计算得出的，极度复杂，基本上计算出来的这个值是唯一的。
     * 隐式声明: 但此处隐藏着一个问题：如果消息的生产者和消息的消费者所参考的类（person11类）有差异，会出现何种神奇事件？
     * <p>
     * 显式声明serialVersionUID可以避免对象不一致，但尽量不要以这种方式向JVM“撒谎”
     */
    @Test
    public void suggestion11_producer() {
        person13 person13 = new person13();
//        person11.setName("混世魔王");
        //序列化,保存到磁盘上
        System.out.println(person13.getName());
        SerializationUtils serializationUtils = new SerializationUtils();
        serializationUtils.writeObject(person13);
    }

    @Test
    public void suggestion11_consumer() {
        //反序列化
        SerializationUtils serializationUtils = new SerializationUtils();
        person13 p = (person13) serializationUtils.readObject();
        System.out.println("name=" + p.getName());
    }

    public class person11 implements Serializable {
        private static final long serialVersionUID = -5799L;
        private String name;
        /*name属性的getter/setter方法省略 混世魔王 德天使 */

//        private String age;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    /**
     * ----------------------suggestion12
     * <p>
     * 第二点 : 在序列化类中，不使用构造函数为final变量赋值
     * <p>
     * 反序列化的另一个规则:   反序列化时构造函数不会执, 而序列化是执行构造函数的
     * <p>
     * 反序列化的执行过程是这样的：JVM从数据流中获取一个Object对象，然后根据数据流中的类文件描述信息
     * （在序列化时，保存到磁盘的对象文件中包含了类描述信息，注意是类描述信息，不是类）查看，发现是 final 变量，需要重新计算，
     * 于是引用person11类中的name值，而此时JVM又发现name竟然没有赋值，不能引用，于是它很“聪明”地不再初始化，保持原值状态，所以结果就是“混世魔王”了
     */
    public class person12 implements Serializable {
        private static final long serialVersionUID = -5799L;
        private final String name;
        /*name属性的getter/setter方法省略 混世魔王 德天使 */

        public person12() {
            this.name = "混世魔王";
        }

        public String getName() {
            return name;
        }
    }

    /**
     *
     */
    public class person13 implements Serializable {
        private static final long serialVersionUID = -5799L;
        private final String name = init();

        private String init() {
            return "德天使";
        }

        public String getName() {
            return name;
        }
    }


    public class SerializationUtils {
        private String FILE_NAME = "d:/obj.bin";
        //序列化

        public void writeObject(Serializable s) {
            try {
                ObjectOutputStream oos = new ObjectOutputStream(new
                        FileOutputStream(FILE_NAME));
                oos.writeObject(s);
                oos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public Object readObject() {
            Object obj = null;
            //反序列化
            try {
                ObjectInput input = new ObjectInputStream(new FileInputStream(FILE_NAME));
                obj = input.readObject();
                input.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return obj;
        }
    }

    /**
     * ----------------- 反序列化, 在以下情况不会被重新赋值
     * <p>
     * 通过构造函数为 final 变量赋值。
     * <p>
     * 通过方法返回值为 final 变量赋值。
     * <p>
     * final 修饰的属性不是基本类型。
     */
    @Test
    public void suggestion13() {
    }

    /**
     * 使用序列化类的私有方法巧妙解决部分属性持久化问题
     * <p>
     * <p>
     * 计税系统只能从HR系统中获得人员姓名和基本工资，而绩效工资是不能获得的，这是个保密数据，不允许发生泄露
     * <p>
     * 方案:
     * （1）在bonus前加上transient关键字
     * <p>
     * 这是一个方法，但不是一个好方法，加上transient关键字就标志着Salary类失去了分布式部署的功能，
     * 它可是HR系统最核心的类了，一旦遭遇性能瓶颈，想再实现分布式部署就不可能了，此方案否定。
     * <p>
     * （2）新增业务对象
     * <p>
     * 增加一个Person4Tax类，完全为计税系统服务，就是说它只有两个属性：姓名和基本工资。符合开闭原则，而且对原系统也没有侵入性，只是增加了工作量而已。这是个方法，但不是最优方法。
     * <p>
     * （3）请求端过滤
     * <p>
     * 在计税系统获得Person对象后，过滤掉Salary的bonus属性，方案可行但不合规矩，因为HR系统中的Salary类安全性竟然让外系统（计税系统）来承担，设计严重失职。
     * <p>
     * （4）变更传输契约
     * <p>
     * 例如改用XML传输，或者重建一个Web Service服务。可以做，但成本太高。
     * <p>
     * 这些方法都有缺陷, 正确的是利用 java序列化的回调机制; 具体详情如下.
     */
    @Test
    public void suggestion14() {

    }

    public class Salary implements Serializable {

        private static final long serialVersionUID = 44663L;
        //基本工资
        private int basePay;
        //绩效工资
        /*transient*/ private int bonus;

        public Salary(int basePay, int bonus) {
            this.basePay = basePay;
            this.bonus = bonus;
        }

        public int getBasePay() {
            return basePay;
        }

        public void setBasePay(int basePay) {
            this.basePay = basePay;
        }


        public int getBonus() {
            return bonus;
        }


        public void setBonus(int bonus) {
            this.bonus = bonus;
        }

        @Override
        public String toString() {
            return "Salary{" +
                    "basePay=" + basePay +
                    ", bonus=" + bonus +
                    '}';
        }
    }

    /**
     * 我们在Person类中增加了writeObject和readObject两个方法，并且访问权限都是私有级别，为什么这会改变程序的运行结果呢？
     * <p>
     * 其实这里使用了序列化独有的机制：序列化回调。
     * --原理
     * Java调用 ObjectOutputStream 类把一个对象转换成流数据时，会通过反射（Reflection）检查被序列化的类是否有writeObject方法，
     * 并且检查其是否符合私有、无返回值的特性。若有，则会委托该方法进行对象序列化，若没有，则由ObjectOutputStream按照默认规则继续序列化。
     * 同样，在从流数据恢复成实例对象时，也会检查是否有一个私有的readObject方法，如果有，则会通过该方法读取属性值。此处有几个关键点要说明：
     * <p>
     * 但是同样如此一来，Person类也失去了分布式部署的能力啊，但是HR系统的难点和重点是薪水计算，特别是绩效工资，
     * 它所依赖的参数很复杂（仅从数量上说就有上百甚至上千种），计算公式也不简单（一般是引入脚本语言，个性化公式定制），
     * 而相对来说Person类基本上都是“静态”属性，计算的可能性不大，所以即使为性能考虑，Person类为分布式部署的意义也不大。
     */
    public class Person implements Serializable {
        private static final long serialVersionUID = 60407L;
        //姓名
        private String name;
        //薪水
        private Salary salary;

        //序列化委托方法
        private void writeObject(ObjectOutputStream out) throws IOException {
            out.defaultWriteObject();
            out.writeInt(salary.getBasePay());
        }

        //反序列化时委托方法
        private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
            in.defaultReadObject();
            salary = new Salary(in.readInt(), 0);
        }


        public Person(String name, Salary salary) {
            this.name = name;
            this.salary = salary;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Salary getSalary() {
            return salary;
        }

        public void setSalary(Salary salary) {
            this.salary = salary;
        }

        @Override
        public String toString() {
            return "Person{" +
                    "name='" + name + '\'' +
                    ", salary=" + salary +
                    '}';
        }
    }

    @Test
    public void suggestion14_Serialize() {
        //基本工资1000元,绩效工资2500元
        Salary salary = new Salary(1000, 2500);
        //记录人员信息
        Person person = new Person("张三", salary);
        //HR系统持久化,并传递到计税系统
        SerializationUtils serializationUtils = new SerializationUtils();
        serializationUtils.writeObject(person);
        System.out.println(person);
    }

    @Test
    public void suggestion14_Deserialize() {
        //技术系统反序列化,并打印信息
        SerializationUtils serializationUtils = new SerializationUtils();
        Person p = (Person) serializationUtils.readObject();
        StringBuffer sb = new StringBuffer();
        sb.append("姓名:" + p.getName());
        sb.append("\t基本工资:" + p.getSalary().getBasePay());
        sb.append("\t绩效工资:" + p.getSalary().getBonus());
        System.out.println(sb);
    }

    /**
     * 建议15：break万万不可忘
     */
    @Test
    public void suggestion15() {
        int i = 0;
        switch (i) {
            case 1:
                System.out.println(11);
                break;
            case 2:
                System.out.println(2);
                break;
            default:
                System.out.println(3);
                break;
        }
    }

    /**
     * 建议16：易变业务使用脚本语言编写
     * <p>
     * 脚本语言的特性:
     * <p>
     * 灵活。脚本语言一般都是动态类型，可以不用声明变量类型而直接使用，也可以在运行期改变类型。
     * <p>
     * 便捷。脚本语言是一种解释型语言，不需要编译成二进制代码，也不需要像Java一样生成字节码。
     * 它的执行是依靠解释器解释的，因此在运行期变更代码非常容易，而且不用停止应用。
     * <p>
     * 简单
     */
    @Test
    public void suggestion16() {
    }

    public static void main(String[] args) throws FileNotFoundException, ScriptException, NoSuchMethodException {
        //获得一个JavaScript的执行引擎
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("javascript");
        //建立上下文变量
        Bindings bind = engine.createBindings();
        bind.put("factor", 1);
        //绑定上下文,作用域是当前引擎范围
        engine.setBindings(bind, ScriptContext.ENGINE_SCOPE);
        Scanner input = new Scanner(System.in);
        while (input.hasNextInt()) {
            int first = input.nextInt();
            int sec = input.nextInt();
            System.out.println("输入参数是:" + first + "," + sec);
            //执行js代码
            engine.eval(new FileReader("d:/model.js"));
            //是否可调用方法
            if (engine instanceof Invocable) {
                Invocable in = (Invocable) engine;
                //执行js中的函数
                Double result = (Double) in.invokeFunction("formula", first, sec);
                System.out.println("运算结果:" + result.intValue());
            }
        }
    }

    /**
     *
     * 建议17：慎用动态编译
     *
     */
    @Test
    public void suggestion17() throws Exception {
//Java源代码
        String sourceStr = "public class Hello{public String sayHello(String name) { return\"Hello,\"+name+\"！\";}}";
//类名及文件名
        String clsName = "Hello";
//方法名
        String methodName = "sayHello";
//当前编译器
        JavaCompiler cmp = ToolProvider.getSystemJavaCompiler();
//Java标准文件管理器
        StandardJavaFileManager fm = cmp.getStandardFileManager(null, null, null);
//Java文件对象
        JavaFileObject jfo = new StringJavaObject(clsName, sourceStr);
//编译参数,类似于javac<options>中的options
        List<String> optionsList = new ArrayList<String>();
//编译文件的存放地方,注意:此处是为Eclipse工具特设的
        optionsList.addAll(Arrays.asList("-d", "./bin"));
//要编译的单元
        List<JavaFileObject> jfos = Arrays.asList(jfo);
//设置编译环境
        JavaCompiler.CompilationTask task = cmp.getTask(null, fm, null, optionsList, null, jfos);
//编译成功
        if (task.call()) {
//生成对象
            Object obj = Class.forName(clsName).newInstance();
            Class<? extends Object> cls = obj.getClass();
//调用sayHello方法
            Method m = cls.getMethod(methodName, String.class);
            String str = (String) m.invoke(obj, "Dynamic Compilation");
            System.out.println(str);
        }
    }
}

//文本中的Java对象
class StringJavaObject extends SimpleJavaFileObject {
    //源代码
    private String content = "";

    //遵循Java规范的类名及文件
    public StringJavaObject(String javaFileName, String content) {


        super(createStringJavaObjectUri(javaFileName), Kind.SOURCE);
        content = content;
    }

    //产生一个URL资源路径
    private static URI createStringJavaObjectUri(String name) {
//注意此处没有设置包名
        return URI.create("String:///" + name + Kind.SOURCE.extension);
    }

    //文本文件代码
    @Override
    public CharSequence getCharContent(boolean ignoreEncodingErrors)
            throws IOException {
        return content;
    }


}





























