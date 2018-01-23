package org.kyll.tax.cccc.dao;

import org.kyll.base.repository.DefaultRepository;
import org.kyll.tax.cccc.domain.PrintBillMsg;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * User: Kyll
 * Date: 2018-01-22 15:09
 */
public interface PrintBillMsgDao extends DefaultRepository<PrintBillMsg, String> {
	Page<PrintBillMsg> findByOpenDateLessThanAndBillCertNoNotAndBillNoNotAndOpenModeInAndOpenPointNotIn(String openDate, String billCertNo, String billNo, List<String> openModeList, List<String> openPointList, Pageable pageable);

	@Query(
			value = "select *\n" +
					"  from (select a.*, rownum rn\n" +
					"          from (select t.print_idx,\n" +
					"                       t.open_idx,\n" +
					"                       t.open_date,\n" +
					"                       t.tax_date,\n" +
					"                       t.total_amt,\n" +
					"                       t.total_tax_amt,\n" +
					"                       t.total,\n" +
					"                       t.bill_cert_no,\n" +
					"                       t.bill_no,\n" +
					"                       t.bill_type,\n" +
					"                       t.open_mode,\n" +
					"                       t.in_name,\n" +
					"                       t.in_taxpayer,\n" +
					"                       t.in_addr_phone,\n" +
					"                       t.in_bank_acc,\n" +
					"                       t.open_point\n" +
					"                  from t_tax_print_bill_msg t\n" +
					"                 where t.open_date < '20180101'\n" +
					"                   and t.bill_cert_no <> '0'\n" +
					"                   and t.bill_no <> '0'\n" +
					"                   and t.open_mode in ('1', '4', '5', '6', '7', '8')\n" +
					"                   and exists\n" +
					"                 (select '1'\n" +
					"                          from t_tax_open_inst t0\n" +
					"                         where t0.acc_inst not in ('FAF000000', 'FAF000001')\n" +
					"                           and t0.open_point = t.open_point)\n" +
					"                 order by t.open_date asc) a\n" +
					"         where rownum <= :end)\n" +
					" where rn >= :start",
			nativeQuery = true)
	List<PrintBillMsg> findByOpenDateLessThanAndBillCertNoNotAndBillNoNotAndOpenModeInAndOpenPointNotIn(@Param("start") int start, @Param("end") int end);
}
