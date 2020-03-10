package dotty.samples._05new

object OpaqueTypeAliases3 {

  // Bounds For Opaque Type Aliases

  // Opaque type aliases can also come with bounds. Example:

  object Access {

    opaque type Permissions = Int
    opaque type PermissionChoice = Int
    opaque type Permission <: Permissions & PermissionChoice = Int
  
    def (x: Permissions) & (y: Permissions): Permissions = x & y
    def (x: PermissionChoice) | (y: PermissionChoice): PermissionChoice = x | y
    def (x: Permissions) is (y: Permissions) = (x & y) == y
    def (x: Permissions) isOneOf (y: PermissionChoice) = (x & y) != 0
  
    val NoPermission: Permission = 0
    val ReadOnly: Permission = 1
    val WriteOnly: Permission = 2
    val ReadWrite: Permissions = ReadOnly & WriteOnly
    val ReadOrWrite: PermissionChoice = ReadOnly | WriteOnly
  }

  object User {

    import Access._
  
    case class Item(rights: Permissions)
  
    val x = Item(ReadOnly)  // OK, since Permission <: Permissions
  
    assert(x.rights.is(ReadWrite) == false)
    assert(x.rights.isOneOf(ReadOrWrite) == true)
  }
}
