package ltd.evilcorp.atox.ui.contactlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import ltd.evilcorp.atox.friendrequest.FriendRequestManager
import ltd.evilcorp.atox.tox.PublicKey
import ltd.evilcorp.atox.tox.Tox
import ltd.evilcorp.core.repository.ContactRepository
import ltd.evilcorp.core.repository.UserRepository
import ltd.evilcorp.core.vo.Contact
import ltd.evilcorp.core.vo.FriendRequest
import ltd.evilcorp.core.vo.User
import javax.inject.Inject

class ContactListViewModel @Inject constructor(
    private val contactRepository: ContactRepository,
    private val userRepository: UserRepository,
    private val friendRequestManager: FriendRequestManager,
    private val tox: Tox
) : ViewModel() {
    val publicKey by lazy { tox.publicKey }
    val toxId by lazy { tox.toxId }

    val contacts: LiveData<List<Contact>> by lazy { contactRepository.getAll() }
    val user: LiveData<User> by lazy { userRepository.get(publicKey.string()) }
    val friendRequests = friendRequestManager.getAll()

    fun isToxRunning() = tox.started

    fun acceptFriendRequest(friendRequest: FriendRequest) = friendRequestManager.accept(friendRequest)
    fun rejectFriendRequest(friendRequest: FriendRequest) = friendRequestManager.reject(friendRequest)

    fun deleteContact(contact: Contact) = tox.deleteContact(PublicKey(contact.publicKey))
}
