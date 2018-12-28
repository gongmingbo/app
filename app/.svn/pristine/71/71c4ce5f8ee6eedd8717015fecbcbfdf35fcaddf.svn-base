package app.service.impl;

import app.entity.content.BaseContent;
import app.entity.content.Tags;
import app.entity.personcenter.UserInfo;
import app.entity.personcenter.UserTags;
import app.entity.user.School;
import app.entity.user.User;
import app.packet.request.CommonPacket;
import app.repository.ContentRepository;
import app.repository.SchoolRepository;
import app.repository.TagRepository;
import app.repository.UserRepository;
import app.service.UserService;
import app.util.AESUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass()) ;

    private final static String sendUrl = "https://api.sms.jpush.cn/v1/codes";
    private final static String validUrl = "https://api.sms.jpush.cn/v1/codes/%s/valid";

    @Value("${sms.appKey}")
    private String appKey;
    @Value("${sms.masterCode}")
    private String masterCode;
    @Value("${uploadpath}")
    private String uploadpath;

    @Autowired
    private SchoolRepository schoolRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private ContentRepository contentRepository;

    @Autowired
    private UserRepository userRepository;
    @Value("${api.session.timeout}")
    private int apiSessionTimeout;
    @Value("${api.session.encryptSeed}")
    private String apiSessionEncryptSeed;
    @Override
    public User getUser(String username) {
        return userRepository.findOne(username);
    }

    @Override
    public User getUserByTelephone(String mobile) {
        return userRepository.findByUserTelephone(mobile);
    }

    @Override
    public boolean validatePassword(String username, String password) {
        User user = getUserByTelephone(username);
        if (user != null ){
            return user.getUserPassword().equals(password);
        }else {
            return false;
        }
    }

    @Override
    public String getToken(String username, String deviceId) {
        String mixed = String .format("%s|%s|%d", username, deviceId, System.currentTimeMillis() + apiSessionTimeout * 60 * 1000);
        return AESUtil.encrypt(mixed, apiSessionEncryptSeed);
    }

    @Override
    public String validateAndRefreshToken(String username, String deviceId, String token) {
        String decrypted = AESUtil.decrypt(token, apiSessionEncryptSeed);
        String[] fields = decrypted.split("\\|");
        if (fields.length == 3){
            String encryptUsername = fields[0];
            String encryptDeviceId = fields[1];
            Long timeMillis = Long.parseLong(fields[2]);
            if (encryptUsername.equals(username) && encryptDeviceId.equals(deviceId)){
                Long currentMillis = System.currentTimeMillis();
                if (timeMillis <= currentMillis){
                    return null;
                }else {
                    if (timeMillis - System.currentTimeMillis() < (apiSessionTimeout - 2) * 60 * 1000){
                        return getToken(username, deviceId);
                    }else {
                        return token;
                    }
                }
            }else {
                return null;
            }
        }else {
            return null;
        }
    }

    @Override
    public String validateAndRefreshToken(CommonPacket packet) {
        return validateAndRefreshToken(packet.getUsername(), packet.getDeviceId(), packet.getToken());
    }

    @Override
    public boolean valid(String user, String messageId, String code) throws UnsupportedEncodingException {
        RestTemplate restTemplate = new RestTemplate();
        ResponseErrorHandler responseErrorHandler = new ResponseErrorHandler() {
            @Override
            public boolean hasError(ClientHttpResponse clientHttpResponse) throws IOException {
                return false;
            }
            @Override
            public void handleError(ClientHttpResponse clientHttpResponse) throws IOException {

            }
        };
        restTemplate.setErrorHandler(responseErrorHandler);
        Map<String,Object > req = new HashMap<>();
        req.put("code", code);
        Gson gson = new Gson();
        MultiValueMap<String ,String > headers = new LinkedMultiValueMap<>();
        headers.add("Content-Type", "application/json");
        headers.add("Authorization", "Basic " + Base64.encodeBase64String((appKey + ":" + masterCode).getBytes("UTF-8")));
        HttpEntity<String> entity = new HttpEntity<>(gson.toJson(req), headers);
        if (messageId != null){
            String res = restTemplate.postForObject(String.format(validUrl, messageId), entity, String .class);
            Map<String ,Object> map = gson.fromJson(res, new TypeToken<Map<String,Object>>(){}.getType());
            boolean isValid = (boolean)map.getOrDefault("is_valid", false);
            if (isValid){
                return true;
            }
            logger.info(res);
        }
        return false;
    }

    @Override
    public String sendSmsCode(String mobile) throws UnsupportedEncodingException {
        RestTemplate restTemplate = new RestTemplate();
        Map<String,Object > req = new HashMap<>();
        req.put("temp_id", 1);
        req.put("mobile", mobile);
        Gson gson = new Gson();
        MultiValueMap<String ,String > headers = new LinkedMultiValueMap<>();
        headers.add("Content-Type", "application/json");
        headers.add("Authorization", "Basic " + Base64.encodeBase64String((appKey + ":" + masterCode).getBytes("UTF-8")));
        HttpEntity<String> entity = new HttpEntity<>(gson.toJson(req), headers);
        try {
            String res = restTemplate.postForObject(sendUrl, entity, String .class);
            Map<String ,String> map = gson.fromJson(res, new TypeToken<Map<String,String>>(){}.getType());
            String msgId = map.getOrDefault("msg_id", "");
            if (!msgId.isEmpty()){
                return msgId;
            }
            return null;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public void newUser(String mobile, String password) {
        User user = new User();
        user.setUserTelephone(mobile);
        user.setUserPassword(password);
        userRepository.save(user);
    }

    //查询用户的学校专业学院年级信息
    public UserInfo sendSchool(User user){
        UserInfo uInfo=new UserInfo();
        if(user.getShoolId()==null||user.getShoolId().equals("")){

        }else{
            School grade=schoolRepository.findByOrgId(user.getShoolId());
            uInfo.setGrade(grade.getOrgName());
            uInfo.setGradeYear(grade.getOrgName());
            uInfo.setGradeId(grade.getOrgId());

            School major=schoolRepository.findByOrgId(grade.getPid());
            uInfo.setMajor(major.getOrgName());
            uInfo.setMajorId(major.getOrgId());

            School college=schoolRepository.findByOrgId(major.getPid());
            uInfo.setCollege(college.getOrgName());
            uInfo.setCollegeId(college.getOrgId());

            School school=schoolRepository.findByOrgId(college.getPid());
            uInfo.setSchool(school.getOrgName());
            uInfo.setSchoolId(school.getOrgId());
        }


        Gson gson=new Gson();
        List<UserTags> tags=gson.fromJson(user.getUserInterest(), new TypeToken<List<UserTags>>() {}.getType());

        uInfo.setSex(user.getUserSex());
        uInfo.setTag(tags);
        uInfo.setTelephone(user.getUserTelephone());
        uInfo.setUserId(user.getUserId());
        uInfo.setUserName(user.getUserName());
        uInfo.setUserImage(user.getUserImage());

        return uInfo;
    }

    //查询所有学校
    public List<Map<String,String>> allSchool(){
        List<Map<String,String>> list=new ArrayList<>();
        List<School> schools=schoolRepository.findByPidIsNull();
        for(int i=0;i<schools.size();i++){
            Map<String,String> map=new HashMap<>();
            map.put("schoolId",schools.get(i).getOrgId());
            map.put("schoolName",schools.get(i).getOrgName());
            list.add(map);
        }
        return list;
    }

    //查询某一学校下的学院或专业或年级信息
    public List<School> allCollege(String pid){
        List<School> list=schoolRepository.findByPid(pid);
        return  list;
    }

    //查询所有标签(分页)
    public List<Tags> allTags1(int size){
        List<Tags> list=tagRepository.findAll();
        int count=list.size();
        //生成随机数
        if(count<size){
            size=count;
        }
        List<Integer> numbers=getRandomNumList(size,0,count);

        List<Tags> tags=new ArrayList<>();
        for(int i=0;i<numbers.size();i++){
            tags.add(list.get(Integer.parseInt(numbers.get(i).toString())));
        }
        return tags;
    }

    //查询所有标签排除选中标签后的 (分页)
    //随机生成size个数据集合
    public List<Tags> allTags2(int size,List<String> ids){
        //Pageable pageable = new PageRequest(page, size);
        //Page<Tags> pg=tagRepository.findByIdNotInOrderById(ids, pageable);

        List<Tags> list=tagRepository.findByIdNotInAndStateInOrderById(ids,true);
        int count=list.size();
        //生成随机数
        if(count<size){
            size=count;
        }
        List<Integer> numbers=getRandomNumList(size,0,count);

        List<Tags> tags=new ArrayList<>();
        for(int i=0;i<numbers.size();i++){
            tags.add(list.get(Integer.parseInt(numbers.get(i).toString())));
        }
        //return pg.getContent();
        return tags;
    }
    //生成随机数
    //定义生成随机数并且装入集合容器的方法
    //方法的形参列表分别为：生成随机数的个数、生成随机数的值的范围最小值为start(包含start)、值得范围最大值为end(不包含end)  可取值范围可表示为[start,end)
    public static List<Integer> getRandomNumList(int size,int start,int end){
        //1.创建集合容器对象
        List<Integer> list = new ArrayList();

        //2.创建Random对象
        Random r = new Random();
        //循环将得到的随机数进行判断，如果随机数不存在于集合中，则将随机数放入集合中，如果存在，则将随机数丢弃不做操作，进行下一次循环，直到集合长度等于nums
        while(list.size() != size){
            int num = r.nextInt(end-start) + start;
            if(!list.contains(num)){
                list.add(num);
            }
        }
        return list;
    }
    /*//查询所有标签总页数
    public int total(int size){
        List<Tags> list=tagRepository.findAll();
        int count=list.size();
        int total=0;
        if(count%size==0){
            total=count/size;
        }else{
            total=(count/size)+1;
        }
        return total;
    }

    //查询所有标签总页数选中后
    public int total(int size,List<Long> ids){
        List<Tags> list=tagRepository.findByIdNotIn(ids);
        int count=list.size();
        return count;
    }*/

    //用户头像图片上传
    public String userImageUpload(@RequestParam("file") MultipartFile file){

        String upload="";
        // /avatar/用户id/图片名
        String imgUrl = uploadpath + "/avatar/";
        // 获取为文件夹下图片名字
        String prevImageName = "";
        //byte[] bytes = Base64.decodeBase64(baseCode64Image);
        File filea = new File(imgUrl);
        if (!filea.exists()) {
            filea.mkdirs();
        }
        //long timeL = Calendar.getInstance().getTimeInMillis() / 1000; // 时间戳精确到秒
        /*Date date=new Date();
        long userTime=Calendar.getInstance().getTimeInMillis();
        String imgUrlName = imgUrl + "/" +userId+ userTime + ".png"; // 图片位置名字
        String name =userId+ userTime +".png";// 图片名字*/
        String oriName = file.getOriginalFilename();
        String name = System.currentTimeMillis() + "_" + oriName;// 图片名字
        String imgUrlName = imgUrl + name;//图片位置名字
        File file1 = new File(imgUrlName);
        try {
            //file1.createNewFile();
            //FileOutputStream fileOutputStream = new FileOutputStream(file1);// 保存图片
            //fileOutputStream.write(bytes);
            //fileOutputStream.close();
            file.transferTo(file1);
            return upload="/resources/avatar/" +name;
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    //查询用户所有收藏
    public List<BaseContent> userMyCollect(String username,int page,int size){
        page=(page-1)*size;
        List<BaseContent> list=contentRepository.collectContent(username,page,size);
        return  list;
    }

    //查询用户所有收藏条数返回总页数
    public int userMyCollectTotalPage(String username,int size){
        int total=0;
        int count=contentRepository.collectContent(username);
        if(count%size==0){
            total=count/size;
        }else{
            total=(count/size)+1;
        }
        return total;
    }

    //查询所有年级
    public List<School> getGrate(List<School> list,String majorId){
        if(list==null||list.size()==0){
            list=new ArrayList<>();
        }
        Date date=new Date();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy");
        int year=Integer.parseInt(sdf.format(date));
        for(int k=year-5;k<year+5;k++){
            int a=0;
            for(int i=0;i<list.size();i++){
                int name=Integer.parseInt(list.get(i).getOrgName());
                if(name==k){
                    a=1;
                }
            }
            //添加年份年级
            if(a==0){
                School s=new School();
                s.setOrgId(getUUID());
                s.setOrgName(k+"");
                s.setPid(majorId);
                schoolRepository.save(s);
            }
        }
        //六月份之前要减5，之后要加5
        Date m=new Date();
        SimpleDateFormat sfd=new SimpleDateFormat("MM");
        String month=sfd.format(m);
        int month1=Integer.parseInt(month);
        List<String> years=new ArrayList<>();
        if(month1>6){
            years.add((year-4)+"");
            years.add((year-3)+"");
            years.add((year-2)+"");
            years.add((year-1)+"");
            years.add((year-0)+"");
        }else{
            years.add((year-4)+"");
            years.add((year-3)+"");
            years.add((year-2)+"");
            years.add((year-1)+"");
            years.add((year-5)+"");
        }
        //String[] years={year-5+"",year-4+"",year-3+"",year-2+"",year-1+"",year+"",year+1+"",year+2+"",year+3+"",year+4+"",year+5+""};
        list=schoolRepository.findByPidAndOrgNameInOrderByOrgName(list.get(0).getPid(),years);
        return list;
    }

    /*
     * 添加或重置课程表的学期等
     * */
    public String getTerm(String grate){
        //获取当前时间判断学期
        Date date=new Date();
        SimpleDateFormat year = new SimpleDateFormat("yyyy");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        int g=Integer.parseInt(grate);//入学年份
        String title="";//学期
        Date begin=null;
        Date end=null;
        String mouth1 = year.format(date)+"-08-01 00:00:00";
        String mouth2 = year.format(date)+"-02-15 00:00:00";
        try {
            begin=sdf.parse(mouth2);
            end=sdf.parse(mouth1);
            if(date.getTime()<begin.getTime()){
                if(Integer.parseInt(year.format(date))-g==1)
                    title="大一上";
                if(Integer.parseInt(year.format(date))-g==2)
                    title="大二上";
                if(Integer.parseInt(year.format(date))-g==3)
                    title="大三上";
                if(Integer.parseInt(year.format(date))-g==4)
                    title="大四上";
                if(Integer.parseInt(year.format(date))-g==5)
                    title="大五上";
            }else if(date.getTime()>end.getTime()){
                if(Integer.parseInt(year.format(date))-g==0)
                    title="大一上";
                if(Integer.parseInt(year.format(date))-g==1)
                    title="大二上";
                if(Integer.parseInt(year.format(date))-g==2)
                    title="大三上";
                if(Integer.parseInt(year.format(date))-g==3)
                    title="大四上";
                if(Integer.parseInt(year.format(date))-g==4)
                    title="大五上";
            }else{
                if(Integer.parseInt(year.format(date))-g==1)
                    title="大一下";
                if(Integer.parseInt(year.format(date))-g==2)
                    title="大二下";
                if(Integer.parseInt(year.format(date))-g==3)
                    title="大三下";
                if(Integer.parseInt(year.format(date))-g==4)
                    title="大四下";
                if(Integer.parseInt(year.format(date))-g==5)
                    title="大五下";
            }
        }catch (Exception e){
            e.getMessage();
        }
        return title;
    }

    //生成uuid
    public static String getUUID(){
        String uuid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
        return uuid;
    }
}
