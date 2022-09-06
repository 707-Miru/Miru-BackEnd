package com.back.miru.model.service;

import com.back.miru.ai.transfer.TransformPainting;
import com.back.miru.model.dao.PictureDAO;
import com.back.miru.model.dto.ListParameterDto;
import com.back.miru.model.dto.Picture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

@Service
public class PictureServiceImpl implements PictureService {
    @Autowired
    private PictureDAO pictureDAO;

    @Override
    public List<Picture> getPictureList(Map<String, String> map) throws Exception {
        int pgno = Integer.parseInt(map.get("page"));
        int countPerPage = 20; // 한 페이지당 보여줄 개수
        int start = (pgno - 1) * countPerPage;
        String sortKeyword = map.getOrDefault("sortKeyword", "time");
        String id = map.get("id");

        ListParameterDto listParameterDto = new ListParameterDto();
        listParameterDto.setStart(start);
        listParameterDto.setCurrentPerPage(countPerPage);
        listParameterDto.setSortKeyword(sortKeyword);
        listParameterDto.setId(id);
        return pictureDAO.selectAllPictures(listParameterDto);
    }

    @Override
    public void registPicture(Map<String, String> map) throws Exception {
        pictureDAO.registPicture(map);
    }

    @Override
    public void deletePicture(String pictureIdx) throws Exception {
        pictureDAO.deletePicture(pictureIdx);
    }

    @Override
    public List<Picture> searchPictureList(String keyword, Map<String, String> map) {
        int pgno = Integer.parseInt(map.get("page"));
        int countPerPage = 20; // 한 페이지당 보여줄 개수
        int start = (pgno - 1) * countPerPage;
        String sortKeyword = map.getOrDefault("sortKeyword", "time");
        String id = map.get("id");
        boolean isPicture = "true".equals(map.getOrDefault("isPicture", null)) ? true : false;

        ListParameterDto listParameterDto = new ListParameterDto();
        listParameterDto.setStart(start);
        listParameterDto.setCurrentPerPage(countPerPage);
        listParameterDto.setKeyword(keyword);
        listParameterDto.setSortKeyword(sortKeyword);
        listParameterDto.setIsPicture(isPicture);
        listParameterDto.setId(id);
        return pictureDAO.searchPictureList(listParameterDto);
    }

    @Override
    public Map<String, Object> getPictureDetail(String pictureIdx) throws Exception {
        return pictureDAO.getPictureDetail(pictureIdx);
    }

    @Override
    public void showPicture(Map<String, String> map) throws Exception {
        int showFlag = Integer.parseInt(map.get("showFlag"));
        String[] pathArr;
        String path = null;

        if (showFlag == 1) {
            path = pictureDAO.getPicturePath(map);
        } else if (showFlag == 2) {
            pathArr = pictureDAO.getPicturePathList(map);
            path = Arrays.toString(pathArr);
            path = path.substring(1, path.length() - 1);
            StringTokenizer st = new StringTokenizer(path, ",");
            StringBuilder sb = new StringBuilder();
            while (st.hasMoreTokens()) {
                String pa = st.nextToken();
                pa = pa.substring(pa.indexOf("img"));
                sb.append(pa).append(",");
            }
            sb.setLength(sb.length() - 1);
            path = sb.toString();
        } else {
            String transferPicturePath = TransformPainting.transform(map.get("option_num"), map.get("style_file_path"), map.get("content_file_path"), map.get("id"));
            path = transferPicturePath.substring(transferPicturePath.indexOf("img"));
        }

        System.out.println("경로 : " + path);

        InetAddress ia = null;
        Socket socket = null;            //Server와 통신하기 위한 Socket
        PrintWriter out = null;            //서버로 내보내기 위한 출력 스트림

        ia = InetAddress.getByName("miru707.ddns.net");    //서버로 접속, 서버 주소 입력
        socket = new Socket(ia, 8888); // 서버가 열어놓은 포트 입력
        out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())));

        out.println(map.get("id") + "," + path);   //서버로 데이터 전송
        out.flush();
        socket.close();
    }
}
