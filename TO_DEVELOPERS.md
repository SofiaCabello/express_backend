# 开发时应当遵守的规范
# 1. Git使用规范
## 1.1 Git分支策略
* main/master分支：主分支，不在此分支上进行开发。
* develop分支：开发分支，所有开发人员在此分支上进行开发。命名规范：`develop`。
* feature分支：功能分支，从develop分支上拉取，开发完成后合并到develop分支。命名规范：`feature/功能名称`。
* release分支：发布分支，从develop分支上拉取，发布完成后合并到develop和main/master分支。命名规范：`release/版本号`。

## 1.2 Git提交规范
* 提交信息应当清晰描述本次提交的内容。**不要使用诸如“修改”、“更新”等无意义的短语。**
* 提交信息应当使用英文，以动词开头。例如：`Add a new feature`。
* 要注意提交粒度，不要一次提交过多的内容，尽量做到一功能一提交。

# 2. 代码规范
## 2.1 代码风格
这个之后再说，但至少不要用拼音命名变量。

命名变量要用英文，且要有意义。

## 2.2 代码注释
每个函数、类、模块都应当有注释，说明其功能、输入、输出等。注释应当使用英文。示例：
```java
/**
 * 该方法用于计算两个整数的和。
 * @param a 被加数。
 * @param b 加数。
 * @return 两个整数的和。
 */
public int add(int a, int b) {
    return a + b;
}
```

对于难以理解的部分，应当添加注释说明。

# 3. API规范
## 3.1 接口设计
接口设计应当遵循RESTful风格，使用HTTP动词表示操作。
尽量将所有操作都归于`GET`、`POST`两种操作（考虑到设计复杂性）。
所有HTTP请求应当返回JSON格式的数据。必须包含message`字段，用于描述请求的结果。示例：
```Java
public Result add(int a, int b) {
    int sum = a + b;
    return Result.ok(sum).message("计算成功");
}
```

## 3.2 接口文档
接口文档应当使用Swagger进行编写，以便于前后端人员查看。
在接口正式上线前，利用Swagger进行接口测试。

