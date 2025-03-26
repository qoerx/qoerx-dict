# Qoerx Dict - 轻量级字典翻译插件

一款基于策略模式的可扩展字典翻译插件，支持多种转换策略。

## ✨ 特性亮点

- 🚀 支持 缓存/枚举 类型的翻译 （支持自定义策略）
- 🔧 支持大部分类型的转换（支持自定义策略）
- 🔄 类型自动转换（支持自定义策略）
- 📦 易于集成（仅需简易集成即可使用）
- 🎯 注解驱动开发

## 📦 快速开始

### 添加依赖
```xml
<dependency>
    <groupId>org.qoerx.dict</groupId>
    <artifactId>qoerx-dict</artifactId>
    <version>1.0.0</version>
</dependency>
```

### 字典数据缓存

具体逻辑参考每个业务系统实现

```java
@DictData
@Service
public class DictDataService implements IDictData {
    @Resource
    private ISysDictTypeService sysDictTypeService;
    @Resource
    private ISysDictDataService sysDictDataService;

    @Override
    public Map<Object, Map> getDictDataMap() {
        Map<Object, Map> map = new HashMap<>();
        List<SysDictType> sysDictTypeList = sysDictTypeService.selectDictTypeList(new SysDictType());
        List<SysDictData> sysDictDataList = sysDictDataService.selectDictDataList(new SysDictData());
        for (SysDictType sysDictType : sysDictTypeList) {
            Map<Object, Object> dictMap = new HashMap<>();
            for (SysDictData sysDictData : sysDictDataList) {
                if (sysDictType.getDictType().equals(sysDictData.getDictType())) {
                    dictMap.put(sysDictData.getDictValue(), sysDictData.getDictLabel());
                }
            }
            map.put(sysDictType.getDictType(), dictMap);
        }
        return map;
    }
}
```

### 基础用法

#### 配置文件

默认为这些值，可以不进行配置，根据具体的项目就行修改

```yaml
dict:
  suffix: "_dict"
  map_key: "data"
```

#### 实体类注解

**code转换获取**

```java
@Dict(code = "sys_yes_no")
private String type;
```

**枚举中获取配置**

```java
@Dict(type = 2, enumClass = Status.class, enumValue = "code", enumTitle = "name")
private Integer status;
```

#### 接口具体使用

```java
@GetMapping("/list")
@DictTransform
public R<List<SysUser>> list(SysUser config)
```

#### 返回结果

```json
{
  "data": {
    "type_dict": "是",
    "type": "Y",
    "name": "dict",
    "status_dict": "正常",
    "status": 1
  },
  "msg": "操作成功",
  "code": 200
}
```

## 📌 说明

本项目主要用于简化springboot字典翻译的操作处理。由于开发者经验所限，当前版本虽无法保证覆盖所有使用场景，但已尽可能确保其能适配大多数常见应用场景。如在特殊业务场景中遇到兼容性问题，或对功能优化有建设性意见，欢迎通过GitHub Issues提交反馈或者私信邮箱18615498639@163.com，我们将持续迭代完善本项目。

