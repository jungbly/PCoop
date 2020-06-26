package pcoop.backend.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import pcoop.backend.dto.DirectoryDTO;
import pcoop.backend.dto.FileDTO;
import pcoop.backend.service.FileService;

@Controller
public class FileController {
	
	@Autowired
	HttpSession session;
	@Autowired
	FileService fservice;
	
	@RequestMapping("fileList")
	public String fileList(Model model) {
		
		String project_name = "temp";
		
		// 드라이브에서 직접 목록 가져올 때
		// String rootDir = session.getServletContext().getRealPath("upload/backup/" + project_name);
		// List<String> dirList = fservice.getDirListFromDrive(rootDir);
		// List<String> fileList = fservice.getFileListFromDrive(rootDir);
		//		for(String s : dirList) {
		//			JsonObject json = new JsonObject();
		//			json.addProperty("seq", 1);
		//			json.addProperty("path", s);
		//			dirArr.add(json);
		//
		//		}
		//		
		//		for(String s : fileList) {
		//			System.out.println(s);
		//			JsonObject json = new JsonObject();
		//			json.addProperty("seq", 1);
		//			json.addProperty("path", s);
		//			fileArr.add(json);
		//
		//		}
		
		// DB에서 목록 가져올 때
		List<DirectoryDTO> dirList = fservice.getDirList();
		List<FileDTO> fileList = fservice.getFileList();
		
		JsonArray dirArr = new JsonArray();
		JsonArray fileArr = new JsonArray();
		
		for(DirectoryDTO dto : dirList) {
			JsonObject json = new JsonObject();
			json.addProperty("seq", dto.getSeq());
			json.addProperty("path", dto.getPath());
			dirArr.add(json);
		}
		
		for(FileDTO dto : fileList) {
			JsonObject json = new JsonObject();
			json.addProperty("seq", dto.getSeq());
			json.addProperty("path", dto.getPath());
			fileArr.add(json);
		}
		
		System.out.println(dirArr);
		model.addAttribute("dirlist", new Gson().toJson(dirArr));
		model.addAttribute("filelist", new Gson().toJson(fileArr));
		return "backup/fileList";
	}

}
