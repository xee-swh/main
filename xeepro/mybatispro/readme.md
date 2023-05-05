# mysql
## 引擎
 innodb
 mysiam


## 数据结构
 innodb底层数据结构采用的是b+树
 b+树:

 
## 区别




## 使用




## 优化



## 日志



## 扩展
mysql主从同步
mysql读写分离
mysql
局部性原理
磁盘预读





















# 常见

## B树与B+树
### B树
这里的B是Balance（平衡）的缩写。它是一种多路的平衡搜索树。
它跟普通的平衡二叉树的不同是，B树的每个节点可以存储多个数据，而且每个节点不止有两个子节点，
最多可以有上千个子节点。

B树中每个节点都存放着索引和数据，数据遍布整个树结构，搜索可能在非叶子节点结束，最好的情况是O(1)。
一般一棵B树的高度在3层左右，3层就可满足百万级别的数据量

### B+树
B+树是B树的一种变种,它与B树的区别是：
    * 叶子节点保存了完整的索引和数据，而非叶子节点只保存索引值，因此它的查询时间固定为 log(n).
    * 叶子节点中有指向下一个叶子节点的指针，叶子节点类似于一个单链表
    * 正因为叶子节点保存了完整的数据以及有指针作为连接，B+树可以增加了区间访问性，提高了范围查询，而B树的范围查询相对较差
    * B+树更适合外部存储。因为它的非叶子节点不存储数据，只保存索引。