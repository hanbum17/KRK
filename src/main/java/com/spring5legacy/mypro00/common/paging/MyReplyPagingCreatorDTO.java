	package com.spring5legacy.mypro00.common.paging;
	
	import java.util.List;
	
	import com.spring5legacy.mypro00.domain.MyReplyVO;
	
	import lombok.Getter;
	import lombok.ToString;
	
	@Getter
	@ToString
	public class MyReplyPagingCreatorDTO {
	
		private MyReplyPagingDTO myReplyPaging;
		private Long rowTotal;
		private List<MyReplyVO> myReplyList;
		
		public MyReplyPagingCreatorDTO(MyReplyPagingDTO myReplyPaging, Long rowTotal, List<MyReplyVO> myReplyList) {
			this.myReplyPaging = myReplyPaging;
			this.rowTotal = rowTotal;
			this.myReplyList = myReplyList;
		}
		
	}
