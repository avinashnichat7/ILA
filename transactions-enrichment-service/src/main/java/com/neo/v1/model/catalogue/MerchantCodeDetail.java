package com.neo.v1.model.catalogue;

import lombok.AllArgsConstructor;
import lombok.Builder;

/**
* Auto generated by ila/neo 1.2.0
*/
@Builder
@AllArgsConstructor
public class MerchantCodeDetail {

	private String id;

	private String code;

	private String revisedName;

	private String logoUrl;

	private CategoryDetail contentfulMerchantCategory;

	private Long displayOrder;


  public MerchantCodeDetail() {
    super();
  }

  /* Getters */

	public String getId() { return this.id; }

	public String getCode() { return this.code; }

	public String getRevisedName() { return this.revisedName; }

	public String getLogoUrl() { return this.logoUrl; }

	public CategoryDetail getContentfulMerchantCategory() { return this.contentfulMerchantCategory; }

	public Long getDisplayOrder() { return this.displayOrder; }


  /* Setters */

	public void setId(String id) { this.id = id; };

	public void setCode(String code) { this.code = code; };

	public void setRevisedName(String revisedName) { this.revisedName = revisedName; };

	public void setLogoUrl(String logoUrl) { this.logoUrl = logoUrl; };

	public void setContentfulMerchantCategory(CategoryDetail contentfulMerchantCategory) { this.contentfulMerchantCategory = contentfulMerchantCategory; };

	public void setDisplayOrder(Long displayOrder) { this.displayOrder = displayOrder; };


  public static class Builder {

    private MerchantCodeDetail item = new MerchantCodeDetail();

		public Builder withId(String id) { item.setId(id); return this; };

		public Builder withCode(String code) { item.setCode(code); return this; };

		public Builder withRevisedName(String revisedName) { item.setRevisedName(revisedName); return this; };

		public Builder withLogoUrl(String logoUrl) { item.setLogoUrl(logoUrl); return this; };

		public Builder withContentfulMerchantCategory(CategoryDetail contentfulMerchantCategory) { item.setContentfulMerchantCategory(contentfulMerchantCategory); return this; };

		public Builder withDisplayOrder(Long displayOrder) { item.setDisplayOrder(displayOrder); return this; };


    public MerchantCodeDetail build() {
      return item;
    }

  }


}
