package idv.dev.brucey.project.controller;

import idv.dev.brucey.project.dto.ResponseDTO;
import idv.dev.brucey.project.enumeration.ResultStatusCode;
import idv.dev.brucey.project.utils.ResponseDTOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@EnableAsync
@RestController
@RequestMapping("/shellAPI")
public class ShellController {

    protected final Logger logger = LoggerFactory.getLogger(ShellController.class);

    @Value("${local.shell.file_path}")
    private String shellFilePath;

    @RequestMapping(value = "/execute/localShell", method = RequestMethod.GET)
    public ResponseDTO executeShell() {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("/bin/bash", shellFilePath);
            Process process = processBuilder.start();
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                logger.info("Script executed successfully");
            } else {
                logger.error("Script execution failed with exit code: " + exitCode);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            logger.error("exception in executeShell method: ", e.getMessage());
            return ResponseDTOUtils.error(ResultStatusCode.EXECUTE_LOCAL_SHELL_FAILURE.getCode(),
                    ResultStatusCode.EXECUTE_LOCAL_SHELL_FAILURE.getMsg());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("exception in executeShell method: ", e.getMessage());
            return ResponseDTOUtils.error(ResultStatusCode.EXECUTE_LOCAL_SHELL_FAILURE.getCode(),
                    ResultStatusCode.EXECUTE_LOCAL_SHELL_FAILURE.getMsg());
        }
        return ResponseDTOUtils.success();
    }
}
