package by.bsu.zakharankou.restservices.service.serviceimpl.transaction;

import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true, noRollbackFor = RuntimeException.class)
public @interface ReadOnlyTransactional {
}
