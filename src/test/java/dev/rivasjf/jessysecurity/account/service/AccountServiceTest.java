package dev.rivasjf.jessysecurity.account.service;

import dev.rivasjf.jessysecurity.account.dto.request.AccountAdditionalInformationUpdateRequestDto;
import dev.rivasjf.jessysecurity.account.dto.request.AccountUpdateRequestDto;
import dev.rivasjf.jessysecurity.account.dto.response.AccountResponseDto;
import dev.rivasjf.jessysecurity.account.entitie.Account;
import dev.rivasjf.jessysecurity.account.entitie.AdditionalInformation;
import dev.rivasjf.jessysecurity.account.entitie.AdditionalInformationType;
import dev.rivasjf.jessysecurity.account.entitie.CategoryAccount;
import dev.rivasjf.jessysecurity.account.repository.AccountRepository;
import dev.rivasjf.jessysecurity.auth.dto.UserRegisterRequestDtoTest;
import dev.rivasjf.jessysecurity.user.entity.User;
import dev.rivasjf.jessysecurity.user.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class AccountServiceTest {

    private static final Logger log = LoggerFactory.getLogger(UserRegisterRequestDtoTest.class);

    @Mock
    private UserRepository userRepository;

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountService accountService;

    private AutoCloseable mocks;

    @BeforeEach
    void setUp() {
        mocks = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        mocks.close();
    }

    @Test
    void updateAccount_ShouldReturn() {
        UUID accountId = UUID.randomUUID();
        UUID passwordInfoId = UUID.randomUUID();
        UUID emailInfoId = UUID.randomUUID();

        User fakeUser = User.create(
                "jon",
                "jon@gmail.com",
                "123",
                "321"
        );

        Account fakeAccount = Account.create(
                fakeUser,
                "AccountTest",
                "user@example.com",
                "This is a test account",
                CategoryAccount.SOCIAL_MEDIA
        );

        ReflectionTestUtils.setField(fakeUser, "publicId", UUID.randomUUID());
        ReflectionTestUtils.setField(fakeAccount, "publicId", accountId);

        AdditionalInformation passwordInfo = AdditionalInformation.create(
                fakeAccount,
                AdditionalInformationType.PASSWORD,
                "1234",
                "321"
        );
        AdditionalInformation emailInfo = AdditionalInformation.create(
                fakeAccount,
                AdditionalInformationType.EMAIL,
                "987",
                "654"
        );
        ReflectionTestUtils.setField(passwordInfo, "publicId", passwordInfoId);
        ReflectionTestUtils.setField(emailInfo, "publicId", emailInfoId);

        List<AdditionalInformation> fakeAdditionalInfoList = new ArrayList<>();
        fakeAdditionalInfoList.add(passwordInfo);
        fakeAdditionalInfoList.add(emailInfo);
        fakeAccount.addAdditionalInformation(fakeAdditionalInfoList);

        when(userRepository.findByEmail("jon@gmail.com")).thenReturn(java.util.Optional.of(fakeUser));
        when(accountRepository.findByUserAndPublicId(fakeUser, accountId)).thenReturn(java.util.Optional.of(fakeAccount));
        when(accountRepository.save(fakeAccount)).thenReturn(fakeAccount);

        List<AccountAdditionalInformationUpdateRequestDto> additionalInfoList = List.of(
                new AccountAdditionalInformationUpdateRequestDto(
                        passwordInfoId.toString(),
                        null,
                        AdditionalInformationType.PASSWORD,
                        "4168834678",
                        "43789329"
                ),
                new AccountAdditionalInformationUpdateRequestDto(
                        emailInfoId.toString(),
                        null,
                        AdditionalInformationType.EMAIL,
                        "4168834678",
                        "43789329"
                )
        );

        var dto = new AccountUpdateRequestDto(
                accountId.toString(),
                "AccountTest",
                "user@example.com",
                "This is a test account",
                CategoryAccount.SOCIAL_MEDIA,
                additionalInfoList
        );

        AccountResponseDto account = accountService.updateAccount("jon@gmail.com", dto);

        assertEquals(accountId.toString(), account.id());
        assertEquals("AccountTest", account.title());
        assertEquals("user@example.com", account.username());
        assertEquals("This is a test account", account.description());
        assertEquals("SOCIAL_MEDIA", account.category());
        assertEquals(2, account.additionalInformation().size());
    }

    @Test
    void Account_ShouldReturn() {
        UUID accountId = UUID.randomUUID();
        UUID passwordInfoId = UUID.randomUUID();
        UUID emailInfoId = UUID.randomUUID();

        User fakeUser = User.create(
                "jon",
                "jon@gmail.com",
                "123",
                "321"
        );

        Account fakeAccount = Account.create(
                fakeUser,
                "AccountTest",
                "user@example.com",
                "This is a test account",
                CategoryAccount.SOCIAL_MEDIA
        );

        ReflectionTestUtils.setField(fakeUser, "publicId", UUID.randomUUID());
        ReflectionTestUtils.setField(fakeAccount, "publicId", accountId);

        AdditionalInformation passwordInfo = AdditionalInformation.create(
                fakeAccount,
                AdditionalInformationType.PASSWORD,
                "1234",
                "321"
        );
        ReflectionTestUtils.setField(passwordInfo, "publicId", passwordInfoId);

        List<AdditionalInformation> fakeAdditionalInfoList = new ArrayList<>();
        fakeAdditionalInfoList.add(passwordInfo);
        fakeAccount.addAdditionalInformation(fakeAdditionalInfoList);

        when(userRepository.findByEmail("jon@gmail.com")).thenReturn(java.util.Optional.of(fakeUser));
        when(accountRepository.findByUserAndPublicId(fakeUser, accountId)).thenReturn(java.util.Optional.of(fakeAccount));
        when(accountRepository.save(any(Account.class))).thenAnswer(invocation -> {
            Account savedAccount = invocation.getArgument(0);
            for (AdditionalInformation info : savedAccount.getAdditionalInformation()) {
                if (info.getPublicId() == null) {
                    ReflectionTestUtils.setField(info, "publicId", emailInfoId);
                }
            }
            return savedAccount;
        });

        List<AccountAdditionalInformationUpdateRequestDto> additionalInfoList = List.of(
                new AccountAdditionalInformationUpdateRequestDto(
                        passwordInfoId.toString(),
                        true,
                        null,
                        null,
                        null
                ),
                new AccountAdditionalInformationUpdateRequestDto(
                        null,
                        null,
                        AdditionalInformationType.EMAIL,
                        "new",
                        "new"
                )
        );

        var dto = new AccountUpdateRequestDto(
                accountId.toString(),
                null,
                null,
                null,
                CategoryAccount.BOOK,
                additionalInfoList
        );

        AccountResponseDto account = accountService.updateAccount("jon@gmail.com", dto);

        log.info(() -> "Account: " + account.toString());

        assertEquals(accountId.toString(), account.id());
        assertEquals("AccountTest", account.title());
        assertEquals("user@example.com", account.username());
        assertEquals("This is a test account", account.description());
        assertEquals("BOOK", account.category());
        assertEquals(1, account.additionalInformation().size());
    }

    @Test
    void updateAccountAdditionalInformation_ShouldReturn() {
        UUID accountId = UUID.randomUUID();
        UUID passwordInfoId = UUID.randomUUID();
        UUID emailInfoId = UUID.randomUUID();

        User fakeUser = User.create(
                "jon",
                "jon@gmail.com",
                "123",
                "321"
        );

        Account fakeAccount = Account.create(
                fakeUser,
                "AccountTest",
                "user@example.com",
                "This is a test account",
                CategoryAccount.SOCIAL_MEDIA
        );

        ReflectionTestUtils.setField(fakeUser, "publicId", UUID.randomUUID());
        ReflectionTestUtils.setField(fakeAccount, "publicId", accountId);

        AdditionalInformation passwordInfo = AdditionalInformation.create(
                fakeAccount,
                AdditionalInformationType.PASSWORD,
                "1234",
                "321"
        );
        AdditionalInformation emailInfo = AdditionalInformation.create(
                fakeAccount,
                AdditionalInformationType.EMAIL,
                "987",
                "654"
        );
        ReflectionTestUtils.setField(passwordInfo, "publicId", passwordInfoId);
        ReflectionTestUtils.setField(emailInfo, "publicId", emailInfoId);

        List<AdditionalInformation> fakeAdditionalInfoList = new ArrayList<>();
        fakeAdditionalInfoList.add(passwordInfo);
        fakeAdditionalInfoList.add(emailInfo);
        fakeAccount.addAdditionalInformation(fakeAdditionalInfoList);

        when(userRepository.findByEmail("jon@gmail.com")).thenReturn(java.util.Optional.of(fakeUser));
        when(accountRepository.findByUserAndPublicId(fakeUser, accountId)).thenReturn(java.util.Optional.of(fakeAccount));
        when(accountRepository.save(fakeAccount)).thenReturn(fakeAccount);

        List<AccountAdditionalInformationUpdateRequestDto> additionalInfoList = List.of(
                new AccountAdditionalInformationUpdateRequestDto(
                        passwordInfoId.toString(),
                        null,
                        AdditionalInformationType.CODE,
                        "new",
                        "new"
                )
        );

        var dto = new AccountUpdateRequestDto(
                accountId.toString(),
                null,
                null,
                null,
                null,
                additionalInfoList
        );

        AccountResponseDto account = accountService.updateAccount("jon@gmail.com", dto);

        assertEquals(accountId.toString(), account.id());
        assertEquals("AccountTest", account.title());
        assertEquals("user@example.com", account.username());
        assertEquals("This is a test account", account.description());
        assertEquals("SOCIAL_MEDIA", account.category());
        assertEquals(2, account.additionalInformation().size());
        assertEquals("CODE", account.additionalInformation().get(0).type());
        assertEquals("new", account.additionalInformation().get(0).value());
        assertEquals("new", account.additionalInformation().get(0).key());
    }

    @Test
    void updateAccountAdditionalInformationSome_ShouldReturn() {
        UUID accountId = UUID.randomUUID();
        UUID passwordInfoId = UUID.randomUUID();
        UUID emailInfoId = UUID.randomUUID();

        User fakeUser = User.create(
                "jon",
                "jon@gmail.com",
                "123",
                "321"
        );

        Account fakeAccount = Account.create(
                fakeUser,
                "AccountTest",
                "user@example.com",
                "This is a test account",
                CategoryAccount.SOCIAL_MEDIA
        );

        ReflectionTestUtils.setField(fakeUser, "publicId", UUID.randomUUID());
        ReflectionTestUtils.setField(fakeAccount, "publicId", accountId);

        AdditionalInformation passwordInfo = AdditionalInformation.create(
                fakeAccount,
                AdditionalInformationType.PASSWORD,
                "1234",
                "321"
        );
        AdditionalInformation emailInfo = AdditionalInformation.create(
                fakeAccount,
                AdditionalInformationType.EMAIL,
                "987",
                "654"
        );
        ReflectionTestUtils.setField(passwordInfo, "publicId", passwordInfoId);
        ReflectionTestUtils.setField(emailInfo, "publicId", emailInfoId);

        List<AdditionalInformation> fakeAdditionalInfoList = new ArrayList<>();
        fakeAdditionalInfoList.add(passwordInfo);
        fakeAdditionalInfoList.add(emailInfo);
        fakeAccount.addAdditionalInformation(fakeAdditionalInfoList);

        when(userRepository.findByEmail("jon@gmail.com")).thenReturn(java.util.Optional.of(fakeUser));
        when(accountRepository.findByUserAndPublicId(fakeUser, accountId)).thenReturn(java.util.Optional.of(fakeAccount));
        when(accountRepository.save(fakeAccount)).thenReturn(fakeAccount);

        List<AccountAdditionalInformationUpdateRequestDto> additionalInfoList = List.of(
                new AccountAdditionalInformationUpdateRequestDto(
                        passwordInfoId.toString(),
                        null,
                        null,
                        "new",
                        null
                )
        );

        var dto = new AccountUpdateRequestDto(
                accountId.toString(),
                null,
                null,
                null,
                null,
                additionalInfoList
        );

        AccountResponseDto account = accountService.updateAccount("jon@gmail.com", dto);

        assertEquals(accountId.toString(), account.id());
        assertEquals("AccountTest", account.title());
        assertEquals("user@example.com", account.username());
        assertEquals("This is a test account", account.description());
        assertEquals("SOCIAL_MEDIA", account.category());
        assertEquals(2, account.additionalInformation().size());
        assertEquals("PASSWORD", account.additionalInformation().get(0).type());
        assertEquals("new", account.additionalInformation().get(0).value());
        assertEquals("321", account.additionalInformation().get(0).key());
    }


}
