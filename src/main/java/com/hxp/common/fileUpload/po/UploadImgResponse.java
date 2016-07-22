package com.hxp.common.fileUpload.po;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * 上传图片返回结果
 *
 */
public class UploadImgResponse {

    private List<FileMeta> files = Lists.newArrayList();

    public void add(Long id,String name, String url, String thumbnail) {
        files.add(new FileMeta(id,name, url, thumbnail));
    }

    public void add(String name, String error) {
        files.add(new FileMeta(name, error));
    }

    public List<FileMeta> getFiles() {
        return files;
    }

    public void setFiles(List<FileMeta> files) {
        this.files = files;
    }

    /**
     * 文件信息
     */
    public static class FileMeta {
        /**
         * 文件记录id
         */
        private Long id;
        /**
         * 名称
         */
        private String name;

        /**
         * 上传文件后的地址
         */
        private String url;
        /**
         * 缩略图
         */
        private String thumbnailUrl;
        /**
         * 错误信息
         */
        private String error;

        public FileMeta( Long id,String name, String url, String thumbnailUrl) {
            this.id = id;
            this.name = name;
            this.url = url;
            this.thumbnailUrl = thumbnailUrl;
        }
        
        public FileMeta(String name, String error) {
            this.name = name;
            this.error = error;
        }

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public String getThumbnailUrl() {
			return thumbnailUrl;
		}

		public void setThumbnailUrl(String thumbnailUrl) {
			this.thumbnailUrl = thumbnailUrl;
		}

		public String getError() {
			return error;
		}

		public void setError(String error) {
			this.error = error;
		}


        
    }
}